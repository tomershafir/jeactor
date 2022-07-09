package org.jeactor.util.concurrent.demux;

import java.util.concurrent.PriorityBlockingQueue;

import org.jeactor.core.Event;
import org.jeactor.util.concurrent.ThreadSafe;

/** 
 * Represents a blocking event demultiplexor that supports prioritization.
 * It collects events passively by consuming them from event sources that originate from the application threads. 
 */
@ThreadSafe
public class PriorityBlockingEventDemux implements EventDemux { 
    // BlockingQueue implementations are thread-safe for single-element operations, and here we have only single element operations
    private final PriorityBlockingQueue<Event> priorityBlockingQueue; 
    
    /** Creates default instance. */
    public PriorityBlockingEventDemux() {
        // eager init to avoid additional application of synchronization in some way needed for lazy init, tradeoff: eager space allocation(which is anyway needed in this context) to avoid performace damage 
        priorityBlockingQueue = new PriorityBlockingQueue<>();
    }

    /**
     * Accepts an event to demultiplex.
     * 
     * @param event an event to demultiplex
     */
    @Override
    public void accept(final Event event) {
        priorityBlockingQueue.add(event);
    }

    /**
     * Returns a collected event.
     * 
     * @return a collected event
     * @throws InterruptedException if interrupted while waiting     
     */
    @Override
    public Event get() throws InterruptedException {
        return priorityBlockingQueue.take();
    }
}
