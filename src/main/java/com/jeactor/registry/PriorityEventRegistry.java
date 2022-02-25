package com.jeactor.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.jeactor.PriorityConsumer;
import com.jeactor.concurrent.Event;

/**
 * Non thread-safe registry that manages the subscription of event consumers to event types.
 */
public class PriorityEventRegistry implements RegistryService<String, PriorityConsumer<Event>> {
    private final Map<String, Queue<PriorityConsumer<Event>>> registryData;

    /**
     * Creates empty event registry. 
     */
    public PriorityEventRegistry() {
        registryData = new HashMap<>();
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType string event type identifier
     * @param handler a consumer of event to associate with the supplied event type
     * @return boolean value indicating wether the subscription succeeded or not
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public void register(final String eventType, final PriorityConsumer<Event> handler) throws NullPointerException {
        if(null == eventType || null == handler)
            throw new NullPointerException();

        Queue<PriorityConsumer<Event>> eventHandlers = registryData.get(eventType);
        if (null == eventHandlers) {
            eventHandlers = new PriorityQueue<PriorityConsumer<Event>>();
            registryData.put(eventType, eventHandlers);
        }
        eventHandlers.add(handler);
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType 
     * @param handler
     * @return boolean value indicating wether the unsubscription succeeded or not
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public void unregister(final String eventType, final PriorityConsumer<Event> handler) throws NullPointerException {
        if (null == eventType || null == handler)
            throw new NullPointerException();

        if (null != registryData) {
            Queue<PriorityConsumer<Event>> eventHandlers = registryData.get(eventType);
            if (null != eventHandlers) {

                // removes an element e such that (handler==null ? e==null : handler.equals(e)), if this list contains such an element
                eventHandlers.remove(handler); 
                
                if(eventHandlers.isEmpty())
                    registryData.remove(eventType);
            }
        } 
    }

    /**
     * The method returns a collection of objects registered with the accepted key.
     * 
     * @param eventType a key by which the collection is returned
     * @return a collection of values registered with the accepted key in the registry, null is returned if the event type is not recorded in the registry
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public Collection<PriorityConsumer<Event>> getRegistered(final String eventType) throws NullPointerException {
        if (null == eventType)
            throw new NullPointerException();
        
        return registryData.get(eventType);
    }
}
