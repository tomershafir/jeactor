package com.jeactor.concurrent;

/**
 * Reresents patterns of events.
 */
public enum EventPattern { // constitue of event class, composition favored over inheritance so event class is final and immutable
    /**
     * Notification event pattern, i.e. minimal payload with back reference for further queries.
     */
    NOTIFICATION,

    /**
     * Event carried state transfer pattern, i.e. payload contains all relevant data.
     */
    STATE_CARRYING
}
