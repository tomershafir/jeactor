package com.jeactor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Unit test of PriorityConsumer. */
public class PriorityConsumerTest extends AbstractJeactorUnitTest {
    /** Creates default instance. */
    public PriorityConsumerTest() {}

    /**
     * Represents a dummy consumer that has a priority.
     * 
     * @param <T> consumed data type
     */
    private static class DummyPriorityConsumer<T> extends PriorityConsumer<T> {
        /** Creates a dummy consumer with NORMAL priority. */
        DummyPriorityConsumer() {
            super();
        }

        /**
         * Creates a dummy consumer with the accepted priority, if it is null than the default is NORMAL.
         * 
         * @param consumerPriority a priority of the consumer
         */
        DummyPriorityConsumer(final Priority consumerPriority) {
            super(consumerPriority);
        }

        /**
         * The method consume data and do nothing.
         *
         * @param data of type T
         */
        @Override
        public void accept(final T data) {}
    }

    /** Tests that compareTo() with null input object throws NPE. */
    @Test
    public void testCompareToNullObjectThrowsNPE() {
        assertThrows(NullPointerException.class, ()->{new DummyPriorityConsumer<String>().compareTo(null);});
    }

    /** Tests that compareTo() with lower priority input object returns positive integer. */
    @Test
    public void testCompareToLowerPriorityObjectReturnsPositiveInt() {
        assertTrue(0 < new DummyPriorityConsumer<String>(Priority.NORMAL).compareTo(new DummyPriorityConsumer<String>(Priority.LOW)));
    }

    /** Tests that compareTo() with higher priority input object returns negative integer. */
    @Test
    public void testCompareToHigherPriorityObjectReturnsNegativeInt() {
        assertTrue(0 > new DummyPriorityConsumer<String>(Priority.LOW).compareTo(new DummyPriorityConsumer<String>(Priority.NORMAL)));
    }

    /** Tests that compareTo() with equal priority input object returns 0. */
    @Test
    public void testCompareToEqualPriorityObjectReturns0() {
        assertEquals(0, new DummyPriorityConsumer<String>(Priority.LOW).compareTo(new DummyPriorityConsumer<String>(Priority.LOW)));
    }
}
