package org.jeactor.registry;

import org.jeactor.AbstractJeactorUnitTest;
import org.jeactor.Event;
import org.jeactor.Priority;
import org.jeactor.PriorityConsumer;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;

/** Unit test of PriorityEventRegistry. */
public class PriorityEventRegistryTest extends AbstractJeactorUnitTest {
    /** Creates default instance. */
    public PriorityEventRegistryTest() {}

    /** The method tests that register() with new event type works as expected. */
    @Test
    public void testRegisterWithUnregisteredEventType() {
        assertTrue(new PriorityEventRegistry().register("dummy", new NopPriorityConsumer<Event>()));
    }

    /** The method tests that register() with existing event type and new handler works as expected. */
    @Test
    public void testRegisterWithRegisteredEventTypeAndUnregisteredHandler() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        registry.register(eventType, new NopPriorityConsumer<Event>());

        assertTrue(registry.register(eventType, new NopPriorityConsumer<Event>()));
    }

    /** The method tests that register() with existing event type and existing handler works as expected. */
    @Test
    public void testRegisterWithRegisteredEventTypeAndRegisteredHandler() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        assertTrue(registry.register(eventType, consumer));
    }

    /** The method tests that unregister() with new event type works as expected. */
    @Test
    public void testUnregisterWithUnregisteredEvent() {
        assertFalse(new PriorityEventRegistry().unregister("dummy", new NopPriorityConsumer<Event>()));
    }

    /** The method tests that unregister() with existing event type and new handler works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndUnregisteredHandler() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        registry.register(eventType, new NopPriorityConsumer<Event>(Priority.CRITICAL));

        assertFalse(registry.unregister(eventType, new NopPriorityConsumer<Event>()));
    }

    /** The method tests that unregister() with existing event type and existing handler works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndRegisteredHandler() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        assertTrue(registry.unregister(eventType, consumer));
    }

    /** The method tests that unregister() with existing event type and more than 1 exisiting handlers works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndMoreThan1RegisteredHandlers() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);
        registry.register(eventType, new NopPriorityConsumer<Event>());

        assertTrue(registry.unregister(eventType, consumer));
    }

    /** The method tests that getRegistered() with new event type returns null. */
    @Test
    public void testGetRegisteredWithUnregisteredEventTypeReturnsNull() {
        assertNull(new PriorityEventRegistry().getRegistered("dummy"));
    }

    /** The method tests that getRegistered() with existing event type and existing handlers returns a collection of the handlers. */
    @Test
    public void testGetRegisteredWithRegisteredEventTypeAndRegisteredHandlersReturnsNewHandlerCollection() {
        final String eventType = "dummy";
        final PriorityEventRegistry registry = new PriorityEventRegistry();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        final Collection<PriorityConsumer<Event>> actual = registry.getRegistered(eventType);

        final PriorityQueue<PriorityConsumer<Event>> expected = new PriorityQueue<PriorityConsumer<Event>>();
        expected.add(consumer);
        assertEquals(new ArrayList<>(expected), new ArrayList<>(actual)); // for equals() implementation of List's jdk's implementations (here ArrayList)
        assertFalse(expected == actual);
    }
}
