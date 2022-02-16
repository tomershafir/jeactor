package com.jeactor;

import java.util.Objects;

import com.jeactor.util.EventPriority;

/**
 * Represents an immutable event.
 */
public final class Event implements Comparable<Event> { // top service layer
    private final String eventType;
    private final EventPriority eventPriority; 

    /**
     * Creates an immutable event of the accepted type and with the accepted priority.
     * 
     * @param eventType a string type of the event
     * @param eventPriority an EventPriority constant that represents the priority of the event
     * @throws NullPointerException when null argument is supplied
     */
    Event(final String eventType, final EventPriority eventPriority) {
        this.eventType = eventType;
        this.eventPriority = eventPriority;
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
