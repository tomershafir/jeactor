package com.jeactor;

/**
 * Factory abstraction that produce reactor instances.
 * 
 * <p>All implementing classes of this interface are thread-safe and maintain a singleton instance.
 */
public interface ConcurrentReactorFactory {
    /**
     * The method returns a reactor product.
     * 
     * @return a reactor instance
     */
    ConcurrentProxyReactor get();
}
