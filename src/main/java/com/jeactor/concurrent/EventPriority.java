package com.jeactor.concurrent;

/**
 * Represents event priority for the reactor.
 */
public enum EventPriority { // ORDER OF CONSTANTS MATTERS, BECAUSE the natural order implemented by the platform is the order in which the constants are declared
    /**
     * Low event priority.
     */
    LOW,

    /**
     * Medium event priority.
     */
    MEDIUM,

    /**
     * High event priority.
     */
    HIGH,

    /**
     * Critical event priority.
     */
    CRITICAL;
}
