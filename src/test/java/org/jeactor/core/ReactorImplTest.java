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
    public void testRegisterWithNullEventTypeThrowsValidationException() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertThrows(ValidationException.class, ()->reactor.register(null, new NopPriorityConsumer<>()));
            }
        );
    }

    /** Tests that register() with null consumer type throws ValidationException. */
    @Test
    public void testRegisterWithNullConsumerThrowsValidationException() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertThrows(ValidationException.class, ()->reactor.register("dummy", null));
            }
        );
    }

    /** Tests that register() registers a consumer with an event type. */
    @Test
    public void testRegister() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertDoesNotThrow(()->reactor.register("dummy", new NopPriorityConsumer<>()));
            }
        );
    }

    /** Tests that unregister() with null event type throws ValidationException. */
    @Test
    public void testUnregisterWithNullEventTypeThrowsValidationException() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertThrows(ValidationException.class, ()->reactor.unregister(null, new NopPriorityConsumer<>()));
            }
        );
    }

    /** Tests that unregister() with null consumer type throws ValidationException. */
    @Test
    public void testUnregisterWithNullConsumerThrowsValidationException() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertThrows(ValidationException.class, ()->reactor.unregister("dummy", null));
            }
        );
    }

    /** Tests that unregister() unregisters a consumer with an event type. */
    @Test
    public void testUnregister() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertDoesNotThrow(()->reactor.unregister("dummy", new NopPriorityConsumer<>()));
            }
        );
    }

    /** Tests that produce() with null event throws ValidationException. */
    @Test
    public void testProduceWithNullEventThrowsValidationException() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertThrows(ValidationException.class, ()->reactor.produce(null));
            }
        );
    }

    /** Tests that produce() produces an event to the reactor. */
    @Test
    public void testProduce() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertDoesNotThrow(()->reactor.produce(new Event("eventType", Priority.NORMAL, EventPattern.NOTIFICATION, "{}", UUID.randomUUID())));
            }
        );
    }

    // TODO: test run()

    // TODO: test close() and interruption and isClosed()

    /** Tests that getExecutorClass() returns the executor class object of the reactor's executor. */
    @Test
    public void testGetExecutorClass() throws Exception {
        testWithResources(
            ()->new ReactorImpl(new SynchronousExecutor()),
            (reactor)->{
                assertEquals(SynchronousExecutor.class, reactor.getExecutorClass());
            }
        );
    }
}
