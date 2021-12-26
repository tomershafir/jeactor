package com.tomer.reactor;

import java.util.function.Consumer;

/**
 * An abstraction of a reactor. Generally areactor should support init, register, unregister, run, shutdown and accept methods.
 * Initialization should be done via instance creation.
 */
public interface Reactor extends Runnable, Consumer<Event> {
    /**
     * The method registers an handler with an event type.
     * 
     * @param eventType
     * @param eventHandler
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws IllegalArgumentException
     */
    public abstract boolean register(String eventType, Consumer<Event> eventHandler) throws IllegalArgumentException;

    /**
     * The method unregisters an handler with an event type.
     * 
     * @param eventType 
     * @param eventHandler
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws IllegalArgumentException
     */
    public boolean unregister(String eventType, Consumer<Event> eventHandler) throws IllegalArgumentException;

    /**
     * The method shuts down the reactor.
     */
    public abstract void shutdown();
}
