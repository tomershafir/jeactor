package com.jeactor;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.jeactor.concurrent.ConcurrentExecutor;
import com.jeactor.demultiplexor.EventDemultiplexor;

/**
 * Basic thread-safe reactor implementation.
 */
class ConcurrentReactor implements Reactor { // top service layer, validations should be included here
    // aliasing is used, necessary where we use collections, need to be careful and avoid unwanted side effects
    private final EventDemultiplexor eventDemultiplexor;
    private final ConcurrentExecutor taskExecutor;
    
    private boolean started;
    private final Object startedLock = new Object();

    private Map<String, List<Consumer<Event>>> observersMap = null;
    private final Lock observersMapLock = new ReentrantLock(true); // fair lock to avoid starvation

    /**
     * Creates a thread safe reactor with the accepted event demultiplexor and task executor.
     * 
     * @param taskExecutor a concurrent executor to use for execution of event handlers when events are dispatched
     * @param eventDemultiplexor a demultiplexor to use for event demultiplexing
     */
    ConcurrentReactor(final ConcurrentExecutor taskExecutor, final EventDemultiplexor eventDemultiplexor) {
        this.eventDemultiplexor = eventDemultiplexor; 
        this.started = false;
        this.taskExecutor = taskExecutor; 
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType string event type identifier
     * @param handler a consumer of event to associate with the supplied event type
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws IllegalArgumentException when null argument is supplied
     */
    @Override
    public void register(final String eventType, final Consumer<Event> handler) throws IllegalArgumentException {
        if(null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");

        observersMapLock.lock();
        try {
            if (null == observersMap)
                observersMap = new HashMap<>();
            List<Consumer<Event>> eventHandlers = observersMap.get(eventType);
            if (null == eventHandlers) {
                eventHandlers = new LinkedList<Consumer<Event>>(); // linked list is suitable for FIFO behaviour of service handler implemented in this reactor implementation
                observersMap.put(eventType, eventHandlers);
            }
            eventHandlers.add(handler);
        } finally {
            observersMapLock.unlock();
        }
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws IllegalArgumentException when null argument is supplied
     */
    @Override
    public void unregister(final String eventType, final Consumer<Event> handler) throws IllegalArgumentException {
        if (null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");

        observersMapLock.lock();
        try {        
            if (null != observersMap) {
                List<Consumer<Event>> eventHandlers = observersMap.get(eventType);
                if (null != eventHandlers) {
                    eventHandlers.remove(handler); // removes an element e such that (handler==null ? e==null : handler.equals(e)), if this list contains such an element
                    if(eventHandlers.isEmpty())
                        observersMap.remove(eventType);
                }
            } 
        } finally {
            observersMapLock.unlock();
        }
    }

    /**
     * The method accepts an event to be processed into the reactor.
     * 
     * @param event an event be processed
     * @throws IllegalArgumentException when null argument is supplied
     */
    @Override
    public void accept(final Event event) {
        if (null == event)
            throw new IllegalArgumentException("Accepted a null argument.");     

        eventDemultiplexor.accept(event);
    }

    /**
     * The method starts the main event loop of the reactor within the current thread.
     * To stop the reactor, interrupt the executor thread.
     * If the singleton reactor has already been started by a thread within the jvm process, calling run() has no effect. 
     */
    @Override
    public void run() {
        synchronized(startedLock) {
            if (started)
                return;
            started = true;
        }

        // only the first thread that acuired startedLock's lock executes the main loop
        try {
            while (true) { // main loop
                final Event event = eventDemultiplexor.get();
                List<Consumer<Event>> eventHandlers = null;

                if (null != event) {
                    observersMapLock.lock();
                    try { 
                        if (null != observersMap) {
                            eventHandlers = observersMap.get(event.type());
                            if (null != eventHandlers) {
                                for (final Consumer<Event> handler : eventHandlers) {
                                    taskExecutor.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.accept(event);
                                        }
                                    });
                                }
                            }
                        }
                    } finally {
                        observersMapLock.unlock();
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
