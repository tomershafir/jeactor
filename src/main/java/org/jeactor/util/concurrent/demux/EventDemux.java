package org.jeactor.util.concurrent.demux;

import java.util.function.Consumer;

import org.jeactor.core.Event;
import org.jeactor.util.concurrent.ThreadSafe;

/** 
 * Represents an event demultiplexor that collects events passively by consuming them from event sources
 * that originate from the application threads.
 * 
 * <p>The implementer must ensure non thread safety compatibility.
 */
@ThreadSafe
public interface EventDemux extends Consumer<Event> {
    /**
     * Returns a collected event.
     * 
     * @return a collected event
     * @throws InterruptedException if interrupted while waiting     
     */
    Event get() throws InterruptedException;
}
