package com.jeactor.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import com.jeactor.PriorityConsumer;
import com.jeactor.concurrent.Event;
import com.jeactor.concurrent.NotThreadSafe;

/** Registry that manages the subscription of event consumers to event types. */
@NotThreadSafe
public class PriorityEventRegistry implements RegistryService<String, PriorityConsumer<Event>> {
    private final HashMap<String, PriorityQueue<PriorityConsumer<Event>>> registryData;

    /** Creates empty event registry. */
    public PriorityEventRegistry() {
        registryData = new HashMap<>();
    }

    /**
     * The method subscribes an handler with an event type.
     * 
     * @param eventType string event type identifier
     * @param handler a consumer of event to associate with the supplied event type
     * @return boolean value indicating wether the redistry state has been changed or not
     */
    @Override
    public boolean register(final String eventType, final PriorityConsumer<Event> handler) {
        PriorityQueue<PriorityConsumer<Event>> eventHandlers = registryData.get(eventType);
        if (null == eventHandlers) {
            eventHandlers = new PriorityQueue<PriorityConsumer<Event>>();
            registryData.put(eventType, eventHandlers);
        }
        return eventHandlers.add(handler);
    }

    /**
     * The method unsubscribes an handler with an event type.
     * 
     * @param eventType string event type identifier
     * @param handler a consumer of event to unregister
     * @return boolean value indicating wether the redistry state has been changed or not
     */
    @Override
    public boolean unregister(final String eventType, final PriorityConsumer<Event> handler) {
        boolean flag = false;
        final PriorityQueue<PriorityConsumer<Event>> eventHandlers = registryData.get(eventType);
        if (null != eventHandlers) {
            flag = eventHandlers.remove(handler); 
            
            if (eventHandlers.isEmpty())
                registryData.remove(eventType);
        }
        return flag;
    }

    /**
     * The method returns a collection of objects registered with the accepted key.
     * 
     * @param eventType a key by which the collection is returned
     * @return a collection of values registered with the accepted key in the registry, null is returned if the event type is not recorded in the registry
     */
    @Override
    public Collection<PriorityConsumer<Event>> getRegistered(final String eventType) {
        final PriorityQueue<PriorityConsumer<Event>> tmp = registryData.get(eventType);
        if (null == tmp)
            return null;
        return new PriorityQueue<PriorityConsumer<Event>>(tmp);
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
        final PriorityEventRegistry that = (PriorityEventRegistry) o;
        return Objects.equals(registryData, that.registryData);
    }

    /**
     * Generates a hash code value for the object.
     * 
     * @return an integer hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(registryData);
    }
}
