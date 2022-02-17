package com.jeactor.concurrent;

import java.util.function.Consumer;

import com.jeactor.concurrent.util.registry.ConcurrentRegistry;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 * 
 * <p>All implementing classes of this interface are thread-safe.
 */
public interface ConcurrentProxyReactor extends Runnable, Consumer<Event>, ConcurrentRegistry<String, Consumer<Event>> {}
