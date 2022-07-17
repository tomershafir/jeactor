package org.jeactor.core;

/** 
 * Represents types that can be started or executed explicitly and directly and returns data on startup.
 * 
 * @param <T> type of the data to return on startup
 */
public interface Startable<T> {
    /**
     * Starts relevant execution.
     * 
     * @return data to return on startup 
     */
    T start();
}
