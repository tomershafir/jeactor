package com.tomer.reactor;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A thread safe abstract observer that handles subscription and notification.
 * 
 * @param <T> data type of consumed events
 */
public abstract class ConcurrentAbstractObserver<T extends Supplier<String>> {
    private Map<String, List<Consumer<T>>> observersMap = null;

    /**
     * No-arg empty constructor.
     */
    protected ConcurrentAbstractObserver(){}

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType
     * @param handler
     * @return boolean value indicating wether the subscription succeeded or not
     */
    public synchronized boolean subscribe(String eventType, Consumer<T> handler){
        if(null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");
        if(null == this.observersMap)
            this.observersMap = new HashMap<>();
        List<Consumer<T>> eventHandlers = this.observersMap.get(eventType);
        if(null == eventHandlers){
            eventHandlers = new ArrayList<Consumer<T>>();
            this.observersMap.put(eventType, eventHandlers);
        }
        eventHandlers.add(handler);
        return true;
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     */
    public synchronized boolean unsubscribe(String eventType, Consumer<T> handler){
        if(null == eventType || null == handler)
            throw new IllegalArgumentException("Accepted a null argument.");
        if(null != this.observersMap){
            List<Consumer<T>> eventHandlers = this.observersMap.get(eventType);
            if(null != eventHandlers){
                Boolean flag = eventHandlers.remove(handler); // removes an element e such that (handler==null ? e==null : handler.equals(e)), if this list contains such an element
                if(eventHandlers.isEmpty())
                    this.observersMap.remove(eventType);
                return flag;
            }
        } 
        return false;
    }
    
    /**
     * The method notifies all handlers associated with the accepted event type.
     * 
     * @param event event object
     */
    public synchronized void notifyAll(T event){
        if(null == event)
            throw new IllegalArgumentException("Accepted a null argument.");
        if(null != this.observersMap){
            List<Consumer<T>> eventHandlers = this.observersMap.get(event.get());
            if(null != eventHandlers)
                for(Consumer<T> handler : eventHandlers)
                    handler.accept(event);
        }
    }    
}
