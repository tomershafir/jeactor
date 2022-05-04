package com.jeactor;

import static org.junit.jupiter.api.Assertions.*;

/** Unit test of PriorityConsumer. */
public class PriorityConsumerTest {
    /** Creates default instance. */
    public PriorityConsumerTest() {}

    /** Tests that compareTo() with null input object throws NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullObjectThrowsNPE() {
        new PriorityConsumer().compareTo(null);
    }

    /** Tests that compareTo() with lower priority input object returns positive integer. */
    @Test
    public void testCompareToLowerPriorityObjectReturnsPositiveInt() {
        assertTrue(0 < new PriorityConsumer(Priority.NORMAL).compareTo(new PriorityConsumer(Priority.LOW));
    }

    /** Tests that compareTo() with higher priority input object returns negative integer. */
    @Test
    public void testCompareToHigherPriorityObjectReturnsNegativeInt() {
        assertTrue(0 > new PriorityConsumer(Priority.LOW).compareTo(new PriorityConsumer(Priority.NORMAL));
    }

    /** Tests that compareTo() with equal priority input object returns 0. */
    @Test
    public void testCompareToEqualPriorityObjectReturns0() {
        assertEquals(0, new PriorityConsumer(Priority.LOW).compareTo(new PriorityConsumer(Priority.LOW));
    }
}
