package com.jeactor;

import java.util.function.Consumer;

import com.jeactor.util.registry.Registry;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 * 
 * <p>All implementing classes of this interface are thread-safe and maintains a singleton.
 */
public interface ProxyReactor extends Runnable, Consumer<Event>, Registry<String, Consumer<Event>> {}
