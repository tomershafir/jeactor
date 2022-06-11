package org.jeactor;

/**
 * Represents a builder object.
 * 
 * @param <T> type of the object to build
 */
public interface Builder<T> {
    /**
     * The method builds an instance.
     * 
     * @return an instance of type T
     */
    T build();
}
