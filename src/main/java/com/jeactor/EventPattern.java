package com.jeactor;

/** Reresents patterns of events. */
public enum EventPattern {
    /** Notification event pattern, i.e. minimal payload with back reference for further queries. */
    NOTIFICATION,

    /** Event carried state transfer pattern, i.e. payload contains all relevant data. */
    STATE_CARRYING
}
