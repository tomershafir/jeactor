package org.jeactor;

import java.util.UUID;

/** Represents an event builder. */
public interface EventBuilder {
    /**
     * Sets event type for the event being built.
     * 
     * @param eventType
     * @return this builder object
     */
    EventBuilderImpl withEventType(String eventType);

    /**
     * Sets event priority for the event being built.
     * 
     * @param eventPriority
     * @return this builder object
     */
    EventBuilderImpl withEventPriority(Priority eventPriority);

    /**
     * Sets event pattern for the event being built.
     * 
     * @param eventPattern
     * @return this builder object
     */
    EventBuilderImpl withEventPattern(EventPattern eventPattern);

    /**
     * Sets json payload for the event being built.
     * 
     * @param jsonPayload
     * @return this builder object
     */
    EventBuilderImpl withJsonPayload(String jsonPayload);

    /**
     * Sets uuid for the event being built.
     * 
     * @param uuid
     * @return this builder object
     */
    EventBuilderImpl withUUID(UUID uuid);
}
