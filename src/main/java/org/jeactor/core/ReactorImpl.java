package org.jeactor.core;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Collection;
import java.util.function.Consumer;
import org.jeactor.util.concurrent.ThreadSafe;
import org.jeactor.util.concurrent.demux.EventDemux;
import org.jeactor.util.concurrent.demux.PriorityBlockingEventDemux;
import org.jeactor.util.registry.PriorityEventRegistryService;
import org.jeactor.util.registry.RegistryService;
import org.jeactor.util.validation.Validations;
import jakarta.validation.ValidationException;

/** Basic reactor implementation. */
@ThreadSafe
class ReactorImpl implements Reactor {
    private final EventDemux eventDemultiplexor;
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
     * @param taskExecutor a concurrent executor to use for execution of event consumers when events are dispatched
     */
    ReactorImpl(final Executor taskExecutor) {
        this.eventDemultiplexor = new PriorityBlockingEventDemux(); 
        this.started = false;
        this.taskExecutor = taskExecutor;
        this.eventRegistry =  new PriorityEventRegistryService();
    }

    /**
     * Registers an consumer with an event type.
     * 
     * @param eventType string event type identifier
     * @param consumer a consumer of event to associate with the supplied event type
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws ValidationException when null argument is supplied
     */
    @Override
    public boolean register(final String eventType, final PriorityConsumer<Event> consumer) throws ValidationException {
        Validations.validateNotNull(eventType, consumer);

        registryLock.lock();
        try {
            return eventRegistry.register(eventType, consumer);
        } finally {
            registryLock.unlock();
        }
    }

    /**
     * Unregisters an consumer with an event type.
     * 
     * @param eventType 
     * @param consumer
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws ValidationException when null argument is supplied
     */
    @Override
    public boolean unregister(final String eventType, final PriorityConsumer<Event> consumer) throws ValidationException {
        Validations.validateNotNull(eventType, consumer);

        registryLock.lock();
        try {        
            return eventRegistry.unregister(eventType, consumer);
        } finally {
            registryLock.unlock();
        }
    }

    /**
     * Produces an event to be processed by reactor.
     * 
     * @param event an event be processed
     * @throws ValidationException when null argument is supplied
     */
    @Override
    public void produce(final Event event) throws ValidationException {
        Validations.validateNotNull(event);
        
        eventDemultiplexor.accept(event);
    }

    /**
     * Starts the main event loop of the reactor within the current thread.
     * 
     * <p>To stop the reactor, interrupt the executor thread.
     * 
     * <p>If this reactor has already been started by a thread within the jvm process, calling run() has no effect. 
     */
    @Override
    public void run() {
        synchronized (startedSynchorinzaionObject) {
            if (started)
                return;
            started = true;
        }

        // only the first thread that acuired startedSynchorinzableObject's lock executes the main loop
        
        try {
            while (true) {
                final Event event = eventDemultiplexor.get();
                Collection<PriorityConsumer<Event>> eventConsumers = null;

                if (null != event) {
                    registryLock.lock();
                    try { 
                        if (null != eventRegistry) {
                            eventConsumers = eventRegistry.getRegistered(event.getEventType());
                            if (null != eventConsumers) {
                                for (final Consumer<Event> consumer : eventConsumers) {
                                    taskExecutor.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            consumer.accept(event);
                                        }
                                    });
                                }
                            }
                        }
                    } finally {
                        registryLock.unlock();
                    }
                }

                // clear interrupted status
                if (Thread.interrupted())  
                    throw new InterruptedException(); 
            }
        } catch(InterruptedException e){
            System.err.println(e.getMessage());
            
            // preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Returns the class of the executor used by the reactor.
     * 
     * @return a class of the executor used by the reactor
     */
    @Override
    public Class<? extends Executor> getExecutorClass() {
        return taskExecutor.getClass();
    }
}
