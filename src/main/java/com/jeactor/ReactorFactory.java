package com.jeactor;

/**
 * Factory abstraction that produce reactor instances.
 */
public interface ReactorFactory {
    /**
     * The method returns a reactor product.
     * 
     * @return a reactor instance
     */
    Reactor get();
}
