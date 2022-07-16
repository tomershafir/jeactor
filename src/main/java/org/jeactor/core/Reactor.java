package org.jeactor.core;

import org.jeactor.util.concurrent.ThreadSafe;
import org.jeactor.util.registry.Registry;

/**
 * Represents a publish-subscribe based reactor. 
 * 
 * <p>Initialization must be implemented via object construction.
 * 
 * <p>Shutdown must be implemented via interrupts.
 */
@ThreadSafe
public interface Reactor extends Runnable, Produceable<Event>, Registry<String, PriorityConsumer<Event>>, ObservableReactor {}
