package org.jeactor.core;

/** 
 * Represents types that can be ran or executed explicitly and directly and returns data on startup.
 * 
 * @param <T> type of the data to return on startup
 */
public interface Runnable<T> {
    /**
     * Executes the object's logic.
     * 
     * @return data to return on startup 
     */
    T run();
}
