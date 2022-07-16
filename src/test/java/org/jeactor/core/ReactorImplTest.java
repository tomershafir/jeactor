package org.jeactor.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.jeactor.AbstractJeactorUnitTest;
import org.jeactor.NopPriorityConsumer;
import org.jeactor.util.concurrent.SynchronousExecutor;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;

/** Unit test of ConcurrentReactor. */
public class ReactorImplTest extends AbstractJeactorUnitTest {
    /** Tests that register() with null event type throws ValidationException. */
    @Test
    public void testRegisterWithNullEventTypeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).register(null, new NopPriorityConsumer<>());});
    }

    /** Tests that register() with null consumer type throws ValidationException. */
    @Test
    public void testRegisterWithNullConsumerThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).register("dummy", null);});
    }

    /** Tests that register() registers a consumer with an event type. */
    @Test
    public void testRegister() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).register("dummy", new NopPriorityConsumer<>());});
    }

    /** Tests that unregister() with null event type throws ValidationException. */
    @Test
    public void testUnregisterWithNullEventTypeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).unregister(null, new NopPriorityConsumer<>());});
    }

    /** Tests that unregister() with null consumer type throws ValidationException. */
    @Test
    public void testUnregisterWithNullConsumerThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).unregister("dummy", null);});
    }

    /** Tests that unregister() unregisters a consumer with an event type. */
    @Test
    public void testUnregister() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).unregister("dummy", new NopPriorityConsumer<>());});
    }

    /** Tests that produce() with null event throws ValidationException. */
    @Test
    public void testProduceWithNullEventThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{new ReactorImpl(new SynchronousExecutor()).produce(null);});
    }

    /** Tests that produce() produces an event to the reactor. */
    @Test
    public void testProduce() {
        assertDoesNotThrow(()->{new ReactorImpl(new SynchronousExecutor()).produce(new Event("eventType", Priority.NORMAL, EventPattern.NOTIFICATION, "{}", UUID.randomUUID()));});
    }


    
    /** Tests that getExecutorClass() returns the executor class object of the reactor's executor. */
    @Test
    public void testGetExecutorClass() {
        assertEquals(SynchronousExecutor.class, new ReactorImpl(new SynchronousExecutor()).getExecutorClass());
    }
}
