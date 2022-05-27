package com.jeactor.concurrent.demux;

import java.util.concurrent.PriorityBlockingQueue;
import com.jeactor.concurrent.Event;
import com.jeactor.concurrent.ThreadSafe;

/** Blocking event demultiplexor that supports prioritization. */
@ThreadSafe
public class ConcurrentPriorityBlockingDemux implements ConcurrentEventDemux { 
    // BlockingQueue implementations are thread-safe for single-element operations, and here we have only single element operations
    private final PriorityBlockingQueue<Event> priorityBlockingQueue; 
    
    /** Creates default event demultiplexor that is blocking, thread-safe and supports prioritization. */
    public ConcurrentPriorityBlockingDemux() {
        // eager init to avoid additional application of synchronization in some way needed for lazy init, tradeoff: eager space allocation(which is anyway needed in this context) to avoid performace damage 
        priorityBlockingQueue = new PriorityBlockingQueue<>();
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

    /**
     * Indicates wether this object equals to the accepted object.
     * 
     * @param o other object to compare this object to
     * @return true if the objects are equal, or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PriorityBlockingQueue that = (PriorityBlockingQueue) o;
        return Objects.equals(priorityBlockingQueue, that.priorityBlockingQueue);
    }

    /**
     * Generates a hash code value for the object.
     * 
     * @return an integer hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(priorityBlockingQueue);
    }
}
