package com.tomer.reactor;

/**
 * Reactor class.
 */
public final class Reactor {
    private static Reactor reactor = null;

    /**
     * Private no-args empty constructor.
     */
    private Reactor(){}

    /**
     * The method returns a singleton intialized reactor.
     */
    public static Reactor reactor(){
        if(null == reactor)
            reactor = new Reactor();
        return reactor;
    }
}
