package com.jeactor;

/**
 * Represents an immutable event.
 */
public final class Event implements Comparable<Event> { // top service layer
    private final String eventType;

    public Event() {
        
    }

    /**
     * The method returns the type of this event.
     * 
     * @return a string type of this event
     */
    public String eventType() {
        return eventType;
    }


}
