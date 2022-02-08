package com.jeactor;

/**
 * Factory abstraction that produce reactor instances.
 * All implementing classes of this interface are thread-safe.
 */
public interface ReactorFactory {
    /**
     * The method returns a reactor product.
     * 
     * @return a reactor instance
     */
    Reactor get();
}
