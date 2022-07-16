package org.jeactor.util.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import org.jeactor.core.Event;
import org.jeactor.core.PriorityConsumer;
import org.jeactor.util.concurrent.NotThreadSafe;

/** Registry that manages the subscription of event consumers to event types. */
@NotThreadSafe
public class PriorityEventRegistryService implements RegistryService<String, PriorityConsumer<Event>> {
    private final HashMap<String, PriorityQueue<PriorityConsumer<Event>>> registryData;

    /** Creates default instance. */
    public PriorityEventRegistryService() {
        registryData = new HashMap<>();
    }

    /**
     * Registeres an handler to the accepted event type.
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
     * Unregisteres an handler from the accepted event type.
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
     * Returns a collection of objects registered with the accepted key.
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
}