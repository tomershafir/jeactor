package com.jeactor;

import java.util.UUID;

import com.jeactor.concurrent.Event;

/**
 * A non thread safe event builder.
 */
public class EventBuilder implements Builder<Event> { // counts on Event's validations lazily
    private String eventType;
    private Priority eventPriority;
    private EventPattern eventPattern;
    private String jsonPayload;
    private long timestamp;
    private UUID uuid;
    
    /**
     * Creates a default builder instance.
     */
    public EventBuilder() {}

    /**
     * Sets event type for the event being built.
     * 
     * @param eventType
     * @return this builder object
     */
    public EventBuilder withEventType(final String eventType) {
        this.eventType = eventType;
        return this;
    }

    /**
     * Sets event priority for the event being built.
     * 
     * @param eventPriority
     * @return this builder object
     */
    public EventBuilder withEventPriority(final Priority eventPriority) {
        this.eventPriority = eventPriority;
        return this;
    }

    /**
     * Sets event pattern for the event being built.
     * 
     * @param eventPattern
     * @return this builder object
     */
    public EventBuilder withEventPattern(final EventPattern eventPattern) {
        this.eventPattern = eventPattern;
        return this;
    }

    /**
     * Sets json payload for the event being built.
     * 
     * @param jsonPayload
     * @return this builder object
     */
    public EventBuilder withJsonPayload(final String jsonPayload) {
        this.jsonPayload = jsonPayload;
        return this;
    }

    /**
     * Sets uuid for the event being built.
     * 
     * @param uuid
     * @return this builder object
     */
    public EventBuilder withUUID(final UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * The method builds a new immutable event.
     * 
     * @return an instance of type T
     */
    public Event build() {
        return new Event(eventType, eventPriority, eventPattern, jsonPayload, uuid);
    }
}
