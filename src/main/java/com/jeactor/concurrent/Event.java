package com.jeactor.concurrent;

import java.util.Objects;

/**
 * Represents an immutable event.
 */
public final class Event implements Comparable<Event> {
    // top service layer
    
    private final String eventType;
    private final Priority eventPriority;
    private final EventPattern eventPattern;
    private final String jsonPayload;

    /**
     * Creates an immutable event of the accepted arguments.
     * 
     * @param eventType a string type of the event
     * @throws NullPointerException when null eventType is supplied
     */
    Event(final String eventType) {
        this(eventType, null, null, null);
    }

    /**
     * Creates an immutable event of the accepted type and with the accepted priority.
     * 
     * @param eventType a string type of the event
     * @param eventPriority an EventPriority constant that represents the priority of the event, if null default to Normal
     * @param eventPattern an EventPattern describing the pattern of the event
     * @param jsonPayload an immutable json string that contains event payload
     * @throws NullPointerException when null eventType is supplied
     */
    Event(final String eventType, final Priority eventPriority, final EventPattern eventPattern, final String jsonPayload) {
        if (null == eventType)
            throw new NullPointerException();
        this.eventType = eventType;
        this.eventPriority = null == eventPriority ? Priority.Normal : eventPriority;
        this.eventPattern = eventPattern;
        this.jsonPayload = jsonPayload;
    }

    /**
     * The method returns the type of this event.
     * 
     * @return a string type of this event
     */
    public String eventType() {
        return eventType;
    }

    /**
     * The method returns the priority of this event.
     * 
     * @return an EventPriority of this event
     */
    public Priority eventPriority() {
        return eventPriority;
    }

    /**
     * The method returns the pattern of this event.
     * 
     * @return an EventPattern of this event
     */
    public EventPattern eventPattern() {
        return eventPattern;
    }

    /**
     * The method returns the payload of this event.
     * 
     * @return a json string that forms of this event
     */
    public String jsonPayload() {
        return jsonPayload;
    }

    /**
     * Compares this object with the specified object for order. 
     * 
     * <p>Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public int compareTo(Event o) {
        if(null == o)
            throw new NullPointerException();
        if(equals(o))
            return 0;
        return eventPriority.compareTo(o.eventPriority);
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
        Event event = (Event) o;
        return Objects.equals(eventType, event.eventType) && Objects.equals(eventPriority, event.eventPriority);
    }

    /**
     * Generates a hash code value for the object.
     * 
     * @return an integer hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(eventType, eventPriority);
    }

    /**
     * Generates a string representation of this object.
     * 
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "Event{" +
                "eventType='" + eventType + '\'' +
                ", eventPriority=" + eventPriority +
                '}';
    }

}
