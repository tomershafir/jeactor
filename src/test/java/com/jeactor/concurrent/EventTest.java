package com.jeactor.concurrent;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import com.jeactor.AbstractJeactorUnitTest;
import com.jeactor.Priority;
import org.junit.jupiter.api.Test;

/** Unit test of Event. */
public class EventTest extends AbstractJeactorUnitTest {
    /** Creates default instance. */
    public EventTest() {}

    /** Tests that compareTo() with null input object throws NPE. */
    @Test
    public void testCompareToNullObjectThrowsNPE() {
        assertThrows(NullPointerException.class, ()->{new Event("dummy", Priority.NORMAL, null, null, UUID.randomUUID()).compareTo(null);});
    }
 
    /** Tests that compareTo() with lower priority input object returns positive integer. */
    @Test
    public void testCompareToLowerPriorityObjectReturnsPositiveInt() {
        assertTrue(0 < new Event("dummy", Priority.NORMAL, null, null, UUID.randomUUID()).compareTo(new Event("dummy", Priority.LOW, null, null, UUID.randomUUID())));
    }
 
    /** Tests that compareTo() with higher priority input object returns negative integer. */
    @Test
    public void testCompareToHigherPriorityObjectReturnsNegativeInt() {
        assertTrue(0 > new Event("dummy", Priority.NORMAL, null, null, UUID.randomUUID()).compareTo(new Event("dummy", Priority.CRITICAL, null, null, UUID.randomUUID())));
    }
 
    /** Tests that compareTo() with equal priority input object returns 0. */
    @Test
    public void testCompareToEqualPriorityObjectReturns0() {
        assertEquals(0, new Event("dummy", Priority.NORMAL, null, null, UUID.randomUUID()).compareTo(new Event("dummy", Priority.NORMAL, null, null, UUID.randomUUID())));
    }
}
