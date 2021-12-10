package com.tomer.reactor.pubsub;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A thread safe abstract obeserver/publisher that handles subscription and notification.
 * 
 * @param <T> data type of consumed events
 */
public abstract class ConcurrentAbstractPublisher<T extends Supplier<String>> extends AbstractPublisher<T> {
    /**
     * No-arg empty constructor.
     */
    protected ConcurrentAbstractPublisher(){
        super();
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType
     * @param handler
     * @return boolean value indicating wether the subscription succeeded or not
     */
    @Override
    public synchronized boolean subscribe(String eventType, Consumer<T> handler){
        return super.subscribe(eventType, handler);
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     */
    @Override
    public synchronized boolean unsubscribe(String eventType, Consumer<T> handler){
        return super.unsubscribe(eventType, handler);
    }
    
    /**
     * The method notifies all handlers associated with the accepted event type.
     * 
     * @param event event object
     */
    @Override
    public synchronized void notifyAll(T event){
        super.notifyAll(event);
    }
}
