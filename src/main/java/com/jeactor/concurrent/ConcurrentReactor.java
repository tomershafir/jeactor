package com.jeactor.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Collection;
import java.util.function.Consumer;

import com.jeactor.concurrent.demultiplexor.ConcurrentEventDemultiplexor;
import com.jeactor.concurrent.registry.ConcurrentRegistryService;

/**
 * Basic thread-safe reactor implementation.
 */
class ConcurrentReactor implements ConcurrentProxyReactor { // top service layer, validations should be included here // aliasing is used, necessary where we use collections, need to be careful from eithin and from without(factory classes) and avoid unwanted side effects // this reactor implementation should include only main loop and wirings to be SOLID
    private final ConcurrentEventDemultiplexor eventDemultiplexor;
    private final Executor taskExecutor;
    
    private boolean started;
    private final Object startedSynchorinzaionObject = new Object();
    
    private final ConcurrentRegistryService<String, Consumer<Event>> eventRegistry; // instance created by factory cannot be exposed or we have thread synchronization problem
    private final Lock registryLock = new ReentrantLock(true); // fair lock to avoid starvation

    /**
     * Creates a thread safe reactor with the accepted event demultiplexor and task executor.
     * 
     * @param taskExecutor a concurrent executor to use for execution of event handlers when events are dispatched
     * @param eventDemultiplexor a demultiplexor to use for event demultiplexing
     * @param eventRegistry a registry service object to be used by the reactor
     */
    ConcurrentReactor(final Executor taskExecutor, final ConcurrentEventDemultiplexor eventDemultiplexor, final ConcurrentRegistryService<String, Consumer<Event>> eventRegistry) {
        this.eventDemultiplexor = eventDemultiplexor; 
        this.started = false;
        this.taskExecutor = taskExecutor;
        this.eventRegistry =  eventRegistry;
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType string event type identifier
     * @param handler a consumer of event to associate with the supplied event type
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public void register(final String eventType, final Consumer<Event> handler) throws NullPointerException {
        if(null == eventType || null == handler)
            throw new NullPointerException();

        registryLock.lock();
        try {
            eventRegistry.register(eventType, handler);
        } finally {
            registryLock.unlock();
        }
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public void unregister(final String eventType, final Consumer<Event> handler) throws NullPointerException {
        if (null == eventType || null == handler)
            throw new NullPointerException();

        registryLock.lock();
        try {        
            eventRegistry.unregister(eventType, handler);
        } finally {
            registryLock.unlock();
        }
    }

    /**
     * The method accepts an event to be processed into the reactor.
     * 
     * @param event an event be processed
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public void accept(final Event event) {
        if (null == event)
            throw new NullPointerException();     

        eventDemultiplexor.accept(event);
    }

    /**
     * The method starts the main event loop of the reactor within the current thread.
     * 
     * <p>To stop the reactor, interrupt the executor thread.
     * 
     * <p>If this reactor has already been started by a thread within the jvm process, calling run() has no effect. 
     */
    @Override
    public void run() {
        synchronized(startedSynchorinzaionObject) {
            if (started)
                return;
            started = true;
        }

        // only the first thread that acuired startedSynchorinzableObject's lock executes the main loop
        try {
            while (true) { // main loop
                final Event event = eventDemultiplexor.get();
                Collection<Consumer<Event>> eventHandlers = null;

                if (null != event) {
                    registryLock.lock();
                    try { 
                        if (null != eventRegistry) {
                            eventHandlers = eventRegistry.getRegistered(event.eventType());
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
                        registryLock.unlock();
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
