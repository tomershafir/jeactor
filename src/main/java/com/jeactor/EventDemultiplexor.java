package com.jeactor;

import java.util.function.Consumer;

/**
 * Represents an event demultiplexor that consume events from event sources and supply events to the dispatcher.
 */
public interface EventDemultiplexor extends Consumer<Event> {
    /**
     * The method waits for events and returns one when available.
     * 
     * @return accepted event
     * @throws InterruptedException if interrupted while waiting     
     */
    public abstract Event get() throws InterruptedException;
}
