package com.jeactor.concurrent;

import java.util.function.Consumer;
import com.jeactor.PriorityConsumer;
import com.jeactor.registry.Registry;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 * 
 * <p>All implementing classes of this interface are thread-safe.
 */
public interface AbstractConcurrentProxyReactor extends Runnable, Consumer<Event>, Registry<String, PriorityConsumer<Event>> {}
