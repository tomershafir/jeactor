package com.jeactor.util.concurrent;

import java.util.concurrent.Executor;

/**
 * Marker interface on top of JDK's executor interface that adds thread-safety semantics.
 * All implementing classes of this interface are thread-safe. 
 */
public interface ConcurrentExecutor extends Executor {}