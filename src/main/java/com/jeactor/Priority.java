package com.jeactor;

/**
 * Represents a generic priority.
 */
public enum Priority {
    // ORDER OF CONSTANTS MATTERS, BECAUSE the natural order implemented by the platform is the order in which the constants are declared
    
    /**
     * Low priority.
     */
    LOW,

    /**
     * Normal priority.
     */
    NORMAL,

    /**
     * High priority.
     */
    HIGH,

    /**
     * Critical priority.
     */
    CRITICAL;
}
