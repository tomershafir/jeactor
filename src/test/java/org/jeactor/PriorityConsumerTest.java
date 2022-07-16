package org.jeactor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jeactor.core.Priority;
import org.junit.jupiter.api.Test;

/** Unit test of PriorityConsumer. */
public class PriorityConsumerTest extends AbstractJeactorUnitTest {
    /** Creates default instance. */
    public PriorityConsumerTest() {}
   
    /** Tests that compareTo() with lower priority input object returns positive integer. */
    @Test
    public void testCompareToLowerPriorityObjectReturnsPositiveInt() {
        assertTrue(0 < new NopPriorityConsumer<String>(Priority.NORMAL).compareTo(new NopPriorityConsumer<String>(Priority.LOW)));
    }

    /** Tests that compareTo() with higher priority input object returns negative integer. */
    @Test
    public void testCompareToHigherPriorityObjectReturnsNegativeInt() {
        assertTrue(0 > new NopPriorityConsumer<String>(Priority.LOW).compareTo(new NopPriorityConsumer<String>(Priority.NORMAL)));
    }

    /** Tests that compareTo() with equal priority input object returns 0. */
    @Test
    public void testCompareToEqualPriorityObjectReturns0() {
        assertEquals(0, new NopPriorityConsumer<String>(Priority.LOW).compareTo(new NopPriorityConsumer<String>(Priority.LOW)));
    }
}
