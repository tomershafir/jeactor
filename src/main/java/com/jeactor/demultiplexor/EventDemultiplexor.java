package com.jeactor.demultiplexor;

import java.util.function.Consumer;

import com.jeactor.Event;

/**
 * Represents an event demultiplexor that consume events from event sources and supply events to the dispatcher.
 * All implementing classes of this interface are thread-safe.
 */
public interface EventDemultiplexor extends Consumer<Event> {
    /**
     * The method returns an accepted event.
     * 
     * @return accepted event
     * @throws InterruptedException if interrupted while waiting     
     */
    Event get() throws InterruptedException;
}
