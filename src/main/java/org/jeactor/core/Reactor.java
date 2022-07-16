package org.jeactor.core;

import org.jeactor.util.concurrent.ThreadSafe;
import org.jeactor.util.registry.Registry;

/**
 * Represents a publish-subscribe based reactor. 
 * 
 * <p>Initialization must be implemented via object construction.
 */
@ThreadSafe
public interface Reactor extends Runnable<Thread>,
    AutoCloseable,
    Registry<String, PriorityConsumer<Event>>, 
    Produceable<Event>, 
    ObservableReactor {}
