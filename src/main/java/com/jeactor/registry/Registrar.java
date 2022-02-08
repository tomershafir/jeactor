package com.jeactor.registry;

/**
 * An interface that represents an object that supports registration of values to keys.
 * All implementing classes of this interface are thread-safe.
 * 
 * @param <K> type of keys to which values are registered
 * @param <V> type of values to be registered
 */
public interface Registrar<K, V> {
    /**
     * The method registers the accepted value with the accepted key.
     * 
     * @param key a key to which the accepted value is registered
     * @param value a value to register
     */
    void register(K key, V value);

    /**
     * The method unregisters the accepted value with the accepted key.
     * 
     * @param key a key from which the accepted value is unregistered
     * @param value a value to unregister
     */
    void unregister(K key, V value);
}
