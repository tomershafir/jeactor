package org.jeactor.registry;

import java.util.Collection;

/**
 * Extension of registry that provides additional utilities.
 * 
 * @param <K> type of keys to which values are registered
 * @param <V> type of values to be registered
 */
public interface RegistryService<K, V> extends Registry<K, V> {
    /**
     * The method returns a collection of objects registered with the accepted key.
     * 
     * @param key a key by which the collection is returned
     * @return a collection of values registered with the accepted key in the registry
     */
    Collection<V> getRegistered(K key) throws NullPointerException;
}
