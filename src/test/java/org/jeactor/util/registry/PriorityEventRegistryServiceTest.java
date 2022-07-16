package org.jeactor.util.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jeactor.AbstractJeactorUnitTest;
import org.jeactor.NopPriorityConsumer;
import org.jeactor.core.Event;
import org.jeactor.core.Priority;
import org.jeactor.core.PriorityConsumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;

/** Unit test of PriorityEventRegistry. */
public class PriorityEventRegistryServiceTest extends AbstractJeactorUnitTest {
    /** Tests that register() with new event type works as expected. */
    @Test
    public void testRegisterWithUnregisteredEventType() {
        assertTrue(new PriorityEventRegistryService().register("dummy", new NopPriorityConsumer<Event>()));
    }

    /** Tests that register() with existing event type and new consumer works as expected. */
    @Test
    public void testRegisterWithRegisteredEventTypeAndUnregisteredConsumer() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        registry.register(eventType, new NopPriorityConsumer<Event>());

        assertTrue(registry.register(eventType, new NopPriorityConsumer<Event>()));
    }

    /** Tests that register() with existing event type and existing consumer works as expected. */
    @Test
    public void testRegisterWithRegisteredEventTypeAndRegisteredConsumer() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        assertTrue(registry.register(eventType, consumer));
    }

    /** Tests that unregister() with new event type works as expected. */
    @Test
    public void testUnregisterWithUnregisteredEvent() {
        assertFalse(new PriorityEventRegistryService().unregister("dummy", new NopPriorityConsumer<Event>()));
    }

    /** Tests that unregister() with existing event type and new consumer works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndUnregisteredConsumer() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        registry.register(eventType, new NopPriorityConsumer<Event>(Priority.CRITICAL));

        assertFalse(registry.unregister(eventType, new NopPriorityConsumer<Event>()));
    }

    /** Tests that unregister() with existing event type and existing consumer works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndRegisteredConsumer() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        assertTrue(registry.unregister(eventType, consumer));
    }

    /** Tests that unregister() with existing event type and more than 1 exisiting consumers works as expected. */
    @Test
    public void testUnregisterWithRegisteredEventAndMoreThan1RegisteredConsumers() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);
        registry.register(eventType, new NopPriorityConsumer<Event>());

        assertTrue(registry.unregister(eventType, consumer));
    }

    /** Tests that getRegistered() with new event type returns null. */
    @Test
    public void testGetRegisteredWithUnregisteredEventTypeReturnsNull() {
        assertNull(new PriorityEventRegistryService().getRegistered("dummy"));
    }

    /** Tests that getRegistered() with existing event type and existing consumers returns a collection of the consumers. */
    @Test
    public void testGetRegisteredWithRegisteredEventTypeAndRegisteredConsumersReturnsNewConsumerCollection() {
        final String eventType = "dummy";
        final PriorityEventRegistryService registry = new PriorityEventRegistryService();
        final NopPriorityConsumer<Event> consumer = new NopPriorityConsumer<Event>();
        registry.register(eventType, consumer);

        final Collection<PriorityConsumer<Event>> actual = registry.getRegistered(eventType);

        final PriorityQueue<PriorityConsumer<Event>> expected = new PriorityQueue<PriorityConsumer<Event>>();
        expected.add(consumer);
        assertEquals(new ArrayList<>(expected), new ArrayList<>(actual)); // for equals() implementation of List's jdk's implementations (here ArrayList)
        assertFalse(expected == actual);
    }
}
