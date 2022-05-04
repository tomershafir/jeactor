package com.jeactor;

import java.util.function.Consumer;

/**
 * Represents a consumer that has a priority.
 * 
 * @param <T> consumed data type
 */
public abstract class PriorityConsumer<T> implements Consumer<T>, Comparable<PriorityConsumer<T>> {
    private final Priority consumerPriority;

    /** Creates a consumer with NORMAL priority. */
    protected PriorityConsumer() {
        this.consumerPriority = Priority.NORMAL;
    }

    /**
     * Creates a consumer with the accepted priority, if it is null than the default is NORMAL.
     * 
     * @param consumerPriority a priority of the consumer
     */
    protected PriorityConsumer(final Priority consumerPriority) {
        this.consumerPriority = null == consumerPriority ? Priority.NORMAL : consumerPriority;
    }

    /**
     * Compares this object with the specified object for order. 
     * 
     * <p>Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object based on the associated priorities.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with Object.equals() and may be inconsistent with other equals() implementations in derived classes.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object based on the associated priorities
     * @throws NullPointerException when null argument is supplied
     */
    @Override
    public final int compareTo(PriorityConsumer<T> o) {
        if(null == o)
            throw new NullPointerException();
        return consumerPriority.compareTo(o.consumerPriority);
    }
}
