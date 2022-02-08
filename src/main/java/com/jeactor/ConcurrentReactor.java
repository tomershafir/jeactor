package com.jeactor;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.jeactor.demultiplexor.EventDemultiplexor;

/**
 * Basic thread-safe reactor implementation.
 */
final class ConcurrentReactor implements Reactor {
    private final EventDemultiplexor demultiplexor;
    private final Executor taskExecutor;
    
    private boolean started;
    private final Object startedLock = new Object();

    private Map<String, List<Consumer<Event>>> observersMap = null;
    private final Lock observersMapLock = new ReentrantLock(true); // fair lock to avoid starvation

    /**
     * Private no-args empty constructor.
     */
    ConcurrentReactor(final Executor executor, final EventDemultiplexor eventDemultiplexor) {
        this.demultiplexor = eventDemultiplexor; //aliasing
        this.started = false;
        this.taskExecutor = executor; //aliasing
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType
     * @param handler
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws IllegalArgumentException
     */
    @Override
    public void register(final String eventType, final Consumer<Event> handler) throws IllegalArgumentException {
        if(null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");
        this.observersMapLock.lock();
        try {
            if(null == this.observersMap)
                this.observersMap = new HashMap<>();
            List<Consumer<Event>> eventHandlers = this.observersMap.get(eventType);
            if(null == eventHandlers){
                eventHandlers = new LinkedList<Consumer<Event>>();
                this.observersMap.put(eventType, eventHandlers);
            }
            eventHandlers.add(handler);
        } finally {
            this.observersMapLock.unlock();
        }
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws IllegalArgumentException
     */
    @Override
    public void unregister(final String eventType, final Consumer<Event> handler) throws IllegalArgumentException {
        if(null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");
        this.observersMapLock.lock();
        try {        
            if(null != this.observersMap){
                List<Consumer<Event>> eventHandlers = this.observersMap.get(eventType);
                if(null != eventHandlers){
                    eventHandlers.remove(handler); // removes an element e such that (handler==null ? e==null : handler.equals(e)), if this list contains such an element
                    if(eventHandlers.isEmpty())
                        this.observersMap.remove(eventType);
                }
            } 
        } finally {
            this.observersMapLock.unlock();
        }
    }

    /**
     * The method accepts an event to be processed into the reactor.
     * 
     * @param event
     * @throws IllegalArgumentException 
     */
    @Override
    public void accept(final Event event) {
        if(null == event)
            throw new IllegalArgumentException("Accepted a null argument.");        
        this.demultiplexor.accept(event);
    }

    /**
     * The method starts the main event loop of the reactor within the current thread.
     * To stop the reactor, interrupt the executor thread.
     * If the singleton reactor has already been started by a thread within the jvm process, calling run() has no effect. 
     */
    @Override
    public void run() {
        synchronized(startedLock){
            if(this.started)
                return;
            this.started = true;
        }

        // only the first thread that acuired startedLock's lock executes the main loop
        try {
            while(true){ // main loop
                // the demultiplexor waits for events
                final Event event = this.demultiplexor.get();
                List<Consumer<Event>> eventHandlers = null;

                if(null != event){
                    this.observersMapLock.lock();
                    try { 
                        if(null != this.observersMap){
                            eventHandlers = this.observersMap.get(event.type());
                            if(null != eventHandlers){
                                for(final Consumer<Event> handler : eventHandlers){
                                    taskExecutor.execute(new Runnable() {
                                        @Override
                                        public void run(){
                                            handler.accept(event);
                                        }
                                    });
                                }
                            }
                        }
                    } finally {
                        this.observersMapLock.unlock();
                    }
                }

                if (Thread.interrupted())  // clears interrupted status
                    throw new InterruptedException(); 
            }
        } catch(InterruptedException e){
            System.err.println(e.getMessage());
            // preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
