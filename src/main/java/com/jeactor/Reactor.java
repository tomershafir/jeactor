package com.jeactor;

import java.util.function.Consumer;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept methods.
 * Initialization may be implemented in construction.
 * Shutdown may be implemented via interrupts.
 */
public interface Reactor extends Runnable, Consumer<Event> {
    /**
     * The method registers an handler with an event type.
     * 
     * @param eventType
     * @param eventHandler
     * @return boolean value indicating wether the subscription succeeded or not
     */
    public abstract boolean register(final String eventType, final Consumer<Event> eventHandler);

    /**
     * The method unregisters an handler with an event type.
     * 
     * @param eventType 
     * @param eventHandler
     * @return boolean value indicating wether the unsubscription succeeded or not
     */
    public boolean unregister(final  String eventType, final Consumer<Event> eventHandler);
}
