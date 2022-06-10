package com.jeactor;

import java.util.Objects;
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
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object based on the associated priorities. If the accepted object is null, a positive integer is returned     */
    @Override
    public final int compareTo(final PriorityConsumer<T> o) {
        if(null == o)
            return 1;
        return consumerPriority.compareTo(o.consumerPriority);
    }

    /**
     * Indicates wether this object equals to the accepted object.
     * 
     * @param o other object to compare this object to
     * @return true if the objects are equal, or false otherwise
     */    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PriorityConsumer<?> that = (PriorityConsumer<?>) o;
        return Objects.equals(consumerPriority, that.consumerPriority);
    }

    /**
     * Generates a hash code value for the object.
     * 
     * @return an integer hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(consumerPriority);
    }
    
    /**
     * Generates a string representation of this object.
     * 
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "PriorityConsumer{" +
                "consumerPriority=" + consumerPriority +
                '}';
    }
}
