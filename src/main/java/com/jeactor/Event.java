package com.jeactor;

/**
 * Represents an event as a comparable object of a certain type.
 */
public interface Event extends Comparable<Event> {
    public abstract String type();
}
