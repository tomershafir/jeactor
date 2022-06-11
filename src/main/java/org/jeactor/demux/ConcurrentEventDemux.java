package org.jeactor.demux;

import java.util.function.Consumer;
import org.jeactor.Event;
import org.jeactor.concurrent.ThreadSafe;

/** Represents an event demultiplexor that consume events from event sources and supply events to the dispatcher. */
@ThreadSafe
public interface ConcurrentEventDemux extends Consumer<Event> {
    /**
     * Returns an accepted event.
     * 
     * @return accepted event
     * @throws InterruptedException if interrupted while waiting     
     */
    Event get() throws InterruptedException;
}
