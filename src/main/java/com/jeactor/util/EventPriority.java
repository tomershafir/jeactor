package com.jeactor.util;

/**
 * Represents event priority for the reactor.
 */
public enum EventPriority {
    /**
     * Low event priority.
     */
    LOW(0),

    /**
     * Medium event priority.
     */
    MEDIUM(1),

    /**
     * High event priority.
     */
    HIGH(2),

    /**
     * Critical event priority.
     */
    CRITICAL(3);

    private final int priority;

    private EventPriority(final int priority) {
        this.priority = priority;
    }

    /**
     * The method returns an integer priority associated with the event priority constant.
     * 
     * @return an integer priority associated with the event priority constant
     */
    public int priority() {
        return priority;
    }
}
