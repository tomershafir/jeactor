package org.jeactor.core;

import jakarta.validation.ValidationException;

/** 
 * Represents an entity to which producers can produce (Produceable rather than Producable).
 * 
 * @param <T> type of objects to produce
 */
public interface Produceable<T> {
    /**
     * Produces a message into the produceable object.
     * 
     * @param message a message be processed
     * @throws ValidationException when null argument is supplied
     */
    void produce(T message) throws ValidationException;
}
