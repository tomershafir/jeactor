package org.jeactor.core;

/**
 * Represents a builder.
 * 
 * @param <T> type to build
 */
public interface Builder<T> {
    /**
     * Builds an instance.
     * 
     * @return an instance of type T
     */
    T build();
}
