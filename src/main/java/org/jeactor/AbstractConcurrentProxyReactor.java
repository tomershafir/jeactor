package org.jeactor;

import java.util.function.Consumer;
import org.jeactor.concurrent.ThreadSafe;
import org.jeactor.registry.Registry;

/**
 * Abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 */
@ThreadSafe
public interface AbstractConcurrentProxyReactor extends Runnable, Consumer<Event>, Registry<String, PriorityConsumer<Event>>, ObservableReactor {}
