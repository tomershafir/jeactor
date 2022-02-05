package com.jeactor;

import java.util.function.Consumer;

import com.jeactor.registry.Registrar;

/**
 * An abstraction of a reactor. Generally a reactor should support init, register, unregister, run, shutdown and accept operations.
 * Initialization may be implemented in construction.
 * Shutdown may be implemented via interrupts.
 */
public interface Reactor extends Runnable, Consumer<Event>, Registrar<String, Consumer<Event>> {}
