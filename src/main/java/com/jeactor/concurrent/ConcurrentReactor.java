package com.jeactor.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Collection;
import java.util.function.Consumer;

import com.jeactor.PriorityConsumer;
import com.jeactor.concurrent.demultiplexor.ConcurrentEventDemux;
import com.jeactor.registry.RegistryService;

/**
 * Basic thread-safe reactor implementation.
 */
class ConcurrentReactor implements AbstractConcurrentProxyReactor {
    // top service layer, validations should be included here 
    // aliasing is used, necessary where we use collections, need to be careful from within and from without(factory classes) and avoid unwanted side effects 
    // this reactor implementation should include only main loop and wirings to be SOLID

    private final ConcurrentEventDemux eventDemultiplexor;
    private final Executor taskExecutor;
    
    private boolean started;
    private final Object startedSynchorinzaionObject = new Object();
    
    // instance created by factory cannot be exposed or we have thread synchronization problem
    private final RegistryService<String, PriorityConsumer<Event>> eventRegistry;

    // fair lock to avoid starvation, but bad effect on performance due to sort exec, also doesnt affect thread shceduling and is not honored by tryLock
    private final Lock registryLock = new ReentrantLock(true); 

    /**
     * Creates a thread safe reactor with the accepted event demultiplexor and task executor.
     * 
     * @param taskExecutor a concurrent executor to use for execution of event handlers when events are dispatched
     * @param eventDemultiplexor a demultiplexor to use for event demultiplexing
     * @param eventRegistry a registry service object to be used by the reactor
     */
    ConcurrentReactor(final Executor taskExecutor, final ConcurrentEventDemux eventDemultiplexor, final RegistryService<String, PriorityConsumer<Event>> eventRegistry) {
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
    public void register(final String eventType, final PriorityConsumer<Event> handler) throws NullPointerException {
        if (null == eventType || null == handler)
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
    public void unregister(final String eventType, final PriorityConsumer<Event> handler) throws NullPointerException {
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
            while (true) {
                final Event event = eventDemultiplexor.get();
                Collection<PriorityConsumer<Event>> eventHandlers = null;

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

                // clears interrupted status
                if (Thread.interrupted())  
                    throw new InterruptedException(); 
            }
        } catch(InterruptedException e){
            System.err.println(e.getMessage());
            
            // preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
