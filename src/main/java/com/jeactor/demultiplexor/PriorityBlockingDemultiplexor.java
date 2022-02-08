package com.jeactor.demultiplexor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.jeactor.Event;

/**
 * Blocking thread-safe implementation of an event demultiplexor that supports prioritization.
 */
public final class PriorityBlockingDemultiplexor implements EventDemultiplexor {
    private final BlockingQueue<Event> priorityBlockingQueue; // BlockingQueue implementations are thread-safe for single-element operations, and here we have only single element operations

    /**
     * Creates default event demultiplexor that is blocking, thread-safe and supports prioritization.
     */
    public PriorityBlockingDemultiplexor() {
        // eager init to avoid additional application of synchronization in some way needed for lazy init, tradeoff: eager space allocation(which is anyway needed in this context) to avoid performace damage 
        priorityBlockingQueue = new PriorityBlockingQueue<>(); // internally a priority heap is used
    }

    /**
     * The method accepts an event to demultiplex.
     * 
     * @param event an event to demultiplex
     */
    @Override
    public void accept(final Event event) {
        priorityBlockingQueue.add(event);
    }

    /**
     * The method returns an accepted event.
     * 
     * @return accepted event
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public Event get() throws InterruptedException {
        return priorityBlockingQueue.take();
    }
}
