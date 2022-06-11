package org.jeactor.concurrent;

import java.util.function.Consumer;
import org.jeactor.ObservableReactor;
import org.jeactor.PriorityConsumer;
import org.jeactor.registry.Registry;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 */
@ThreadSafe
public interface AbstractConcurrentProxyReactor extends Runnable, Consumer<Event>, Registry<String, PriorityConsumer<Event>>, ObservableReactor {}
