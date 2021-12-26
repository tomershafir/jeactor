package com.tomer.reactor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Thread safe sync event demultiplexor of the reactor.
 */
class ConcurrentSyncEventDemultiplexor implements EventDemultiplexor {
    private static ConcurrentSyncEventDemultiplexor demultiplexor = null;
    private BlockingQueue<Event> blockingQueue; // BlockingQueue implementations are thread-safe for single-element operations

    /**
     * Private no-args empty constructor.
     */
    private ConcurrentSyncEventDemultiplexor(){
        this.blockingQueue = new LinkedBlockingQueue<>();
    }

    /**
     * The method returns a singleton intialized demultiplexor.
     * 
     * @return a singleton reactor
     */
    synchronized static ConcurrentSyncEventDemultiplexor demultiplexor(){
        if(null == demultiplexor)
            demultiplexor = new ConcurrentSyncEventDemultiplexor();
        return demultiplexor;
    }

    /**
     * The method accepts an event.
     * 
     * @param event
     */
    @Override
    public void accept(Event event){
        this.blockingQueue.add(event);
    }

    /**
     * The method returns an accepted event.
     * 
     * @return accepted event
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public Event get() throws InterruptedException {
        return this.blockingQueue.take();
    }
}
