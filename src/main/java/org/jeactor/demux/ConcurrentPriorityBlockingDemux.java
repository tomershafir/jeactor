package org.jeactor.demux;

import java.util.concurrent.PriorityBlockingQueue;
import org.jeactor.Event;
import org.jeactor.concurrent.ThreadSafe;

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
     * Accepts an event to demultiplex.
     * 
     * @param event an event to demultiplex
     */
    @Override
    public void accept(final Event event) {
        priorityBlockingQueue.add(event);
    }

    @Override
    public Event get() throws InterruptedException {
        return priorityBlockingQueue.take();
    }
}
