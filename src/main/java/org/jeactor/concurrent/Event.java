package org.jeactor.concurrent;

import java.util.Objects;
import org.jeactor.EventPattern;
import org.jeactor.Priority;
import org.jeactor.validation.Validations;
import jakarta.validation.ValidationException;

import java.util.UUID;

/** Represents an immutable event. */
@ThreadSafe
public final class Event implements Comparable<Event> {
    private final String eventType;
    private final Priority eventPriority;
    private final EventPattern eventPattern;
    private final String jsonPayload;
    private final Long timestamp;
    private final UUID uuid;

    /**
     * Creates an immutable event of the accepted type and with the accepted priority.
     * 
     * @param eventType a string type of the event
     * @param eventPriority an EventPriority constant that represents the priority of the event, if null default to Normal
     * @param eventPattern an EventPattern describing the pattern of the event
     * @param jsonPayload an immutable json string that contains event payload
     * @param uuid a uuid for the event
     * @throws ValidationException when null eventType or uuid supplied
     */
    public Event(final String eventType, final Priority eventPriority, final EventPattern eventPattern, final String jsonPayload, final UUID uuid) throws ValidationException {
        Validations.validateNotNull(eventType, uuid);
        timestamp = System.currentTimeMillis();
        this.uuid = uuid;
        this.eventType = eventType;
        this.eventPriority = null == eventPriority ? Priority.NORMAL : eventPriority;
        this.eventPattern = eventPattern;
        this.jsonPayload = jsonPayload;
    }

    /**
     * The method returns the type of this event.
     * 
     * @return a string type of this event
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * The method returns the priority of this event.
     * 
     * @return an EventPriority of this event
     */
    public Priority getEventPriority() {
        return eventPriority;
    }

    /**
     * The method returns the pattern of this event.
     * 
     * @return an EventPattern of this event
     */
    public EventPattern getEventPattern() {
        return eventPattern;
    }

    /**
     * The method returns the payload of this event.
     * 
     * @return a json string that forms of this event
     */
    public String getJsonPayload() {
        return jsonPayload;
    }

    /**
     * The method returns the timestamp of this event.
     * 
     * @return a long timestamp of this event
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * The method returns the uuid of this event.
     * 
     * @return a uuid of this event
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Compares this object with the specified object for order. 
     * 
     * <p>Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object based on the associated priorities and timestamps.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object based on the associated priorities. If the accepted object is null, a positive integer is returned
     */
    @Override
    public int compareTo(final Event o) {
        if (null == o)
            return 1;
        final int res = eventPriority.compareTo(o.eventPriority);
        if (0 != res)
            return res;
        return timestamp.compareTo(o.timestamp);
    }

    /**
     * Indicates wether this object equals to the accepted object.
     * 
     * @param o other object to compare this object to
     * @return true if the objects are equal, or false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Event event = (Event) o;
        return Objects.equals(eventType, event.eventType) && 
            Objects.equals(eventPriority, event.eventPriority) && 
            Objects.equals(eventPattern, event.eventPattern) && 
            Objects.equals(jsonPayload, event.jsonPayload) &&
            Objects.equals(timestamp, event.timestamp) &&
            Objects.equals(uuid, event.uuid);
    }

    /**
     * Generates a hash code value for the object.
     * 
     * @return an integer hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(eventType, eventPriority, eventPattern, jsonPayload, timestamp, uuid);
    }

    /**
     * Generates a string representation of this object.
     * 
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", eventPriority=" + eventPriority +
                ", eventPattern=" + eventPattern +
                ", jsonPayload=" + jsonPayload +
                ", timestamp=" + timestamp +
                ", uuid=" + uuid +
                '}';
    }
}
