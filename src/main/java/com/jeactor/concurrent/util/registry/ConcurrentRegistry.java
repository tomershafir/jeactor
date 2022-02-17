package com.jeactor.concurrent.util.registry;

/**
 * An interface that represents an object that supports registration of values to keys.
 * 
 * <p>All implementing classes of this interface are thread-safe.
 * 
 * @param <K> type of keys to which values are registered
 * @param <V> type of values to be registered
 */
public interface ConcurrentRegistry<K, V> {
    /**
     * The method registers the accepted value with the accepted key.
     * 
     * @param key a key to which the accepted value is registered
     * @param value a value to register
     * @throws NullPointerException when null argument is supplied
     */
    void register(K key, V value) throws NullPointerException;

    /**
     * The method unregisters the accepted value with the accepted key.
     * 
     * @param key a key from which the accepted value is unregistered
     * @param value a value to unregister
     * @throws NullPointerException when null argument is supplied
     */
    void unregister(K key, V value) throws NullPointerException;
}