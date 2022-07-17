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

    private final RegistryService<String, PriorityConsumer<Event>> eventRegistry;
    private final Lock registryLock; 

    private boolean started;
    private final Lock startLock;

    private boolean closed;
    private final Lock closeLock;

    private Thread backgroundThread;


    /**
     * Creates a thread safe reactor with the accepted task executor.
     * 
     * @param taskExecutor a concurrent executor to use for execution of event consumers when events are dispatched
     */
    ReactorImpl(final Executor taskExecutor) {
        this.eventDemultiplexor = new PriorityBlockingEventDemux();

        // instance created by factory must not be exposed or we have aliasing problem
        this.taskExecutor = taskExecutor;

        this.started = false;
        startLock = new ReentrantLock();

        closed = false;
        closeLock = new ReentrantLock();

        this.eventRegistry =  new PriorityEventRegistryService();

        // fair lock to avoid starvation, but bad effect on performance due to sort exec, also doesnt affect thread scheduling and is not honored by tryLock
        registryLock = new ReentrantLock(true);
    }

    /**
     * Starts the main event loop of the reactor in a new background thread and returns a reference to that thread.
     * 
     * <p>To stop the reactor, interrupt the background thread or call close().
     * 
     * <p>If this reactor has already been started by a thread, or has already been closed, calling run() has no effect. 
     * 
     * @return a thread reference to the background thread that executes the main event loop
     */
    @Override
    public Thread start() {
        startLock.lock();
        try {
            // nested locking here is not problematic considering deadlock because ther is no opposite way of nesting
            if (!started && !isClosed()) {
                started = true;
                
                // only the first thread that acquires startLock spawns a new thread
                // we must return here because otherwise we may have a deadlock (because the executing thread holds startLock indefinitely)
                backgroundThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                final Event event = eventDemultiplexor.get();
                                
                                dispatch(event);
                
                                // clears interrupted status
                                if (Thread.interrupted())  
                                    throw new InterruptedException(); 
                            }
                        } catch (final InterruptedException e) {
                            handleInterrupt(e);
                        }         
                    }
                };
                backgroundThread.start();
            }

            // backgroundThread here for sure has already been initialized by the first thread that acquired startLock
            return backgroundThread;
        } finally {
            startLock.unlock();
        }
    }

    /**
     * Dispathces the accepted event.
     * 
     * @param event an event to dispatch
     */
    private void dispatch(final Event event) {
        Collection<PriorityConsumer<Event>> eventConsumers = null;
                
        if (null != event) {
            registryLock.lock();
            try { 
                if (null != eventRegistry) {
                    eventConsumers = eventRegistry.getRegistered(event.getEventType());
                    if (null != eventConsumers) {
                        for (final Consumer<Event> consumer : eventConsumers) {
                            taskExecutor.execute(new java.lang.Runnable() {
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
    }

    /**
     * Handles interruption of the reactor's background thread.
     * 
     * <p>Interrupting the reactor's background thread will always result in InterruptedException being thrown, 
     * due to the combination of catch and periodic polling (and then throwing InterruptedException if interrupted).
     * 
     * @param e thrown interrupted exception
     */
    private void handleInterrupt(final InterruptedException e) {
        // TODO: add record in form of logs, metrics, traces

        // preserve interrupt status
        Thread.currentThread().interrupt(); // <=> backgroundThread.interrupt()

        closeLock.lock();
        try {
            closed = true;
        } finally {
            closeLock.unlock();
        }
    }

    /**
     * Closes the reactor and the resources associated with it. If the reactor hasn't been started yet, close() has no effect.
     * 
     * @throws Exception if an error occurs
     */
    @Override
    public void close() {
        startLock.lock();
        try {
            if (started)
                backgroundThread.interrupt();
        } finally {
            startLock.unlock();
        }
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
     * Returns the class of the executor used by the reactor.
     * 
     * @return a class of the executor used by the reactor
     */
    @Override
    public Class<? extends Executor> getExecutorClass() {
        return taskExecutor.getClass();
    }

    /**
     * Returns wether the reator has been closed or not.
     * 
     * @return a boolean indicating wether the reator has been closed or not
     */
    @Override
    public boolean isClosed() {
        closeLock.lock();
        try {
            return closed;
        } finally {
            closeLock.unlock();
        }
    }
}
