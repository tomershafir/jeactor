package org.jeactor.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.jeactor.AbstractJeactorUnitTest;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;

/** Unit test of Event. */
public class EventTest extends AbstractJeactorUnitTest {
    /** Tests that an event cannot be created with null event type. */
    @Test
    public void testEventWithNullEventTypeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->new Event(null, Priority.NORMAL, null, null, UUID.randomUUID()));
    }

    /** Tests that an event cannot be created with null uuid. */
    @Test
    public void testEventWithNullUUIDThrowsValidationException() {
        assertThrows(ValidationException.class, ()->new Event("dummy", Priority.NORMAL, null, null, null));
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
