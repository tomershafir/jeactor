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
        assertThrows(ValidationException.class, ()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.register(null, new NopPriorityConsumer<>());
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that register() with null consumer type throws ValidationException. */
    @Test
    public void testRegisterWithNullConsumerThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.register("dummy", null);
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that register() registers a consumer with an event type. */
    @Test
    public void testRegister() {
        assertDoesNotThrow(()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.register("dummy", new NopPriorityConsumer<>());
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that unregister() with null event type throws ValidationException. */
    @Test
    public void testUnregisterWithNullEventTypeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.unregister(null, new NopPriorityConsumer<>());
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that unregister() with null consumer type throws ValidationException. */
    @Test
    public void testUnregisterWithNullConsumerThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.unregister("dummy", null);
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that unregister() unregisters a consumer with an event type. */
    @Test
    public void testUnregister() {
        assertDoesNotThrow(()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.unregister("dummy", new NopPriorityConsumer<>());
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that produce() with null event throws ValidationException. */
    @Test
    public void testProduceWithNullEventThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.produce(null);
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }

    /** Tests that produce() produces an event to the reactor. */
    @Test
    public void testProduce() {
        assertDoesNotThrow(()->{
            Reactor reactor = null;
            try {
                reactor = new ReactorImpl(new SynchronousExecutor());
                reactor.produce(new Event("eventType", Priority.NORMAL, EventPattern.NOTIFICATION, "{}", UUID.randomUUID()));
            } finally {
                if (null != reactor)
                    reactor.close();
            }
        });
    }



    /** Tests that getExecutorClass() returns the executor class object of the reactor's executor. */
    @Test
    public void testGetExecutorClass() throws Exception{
        Reactor reactor = null;
        try {
            reactor = new ReactorImpl(new SynchronousExecutor());
            assertEquals(SynchronousExecutor.class, reactor.getExecutorClass());
        } finally {
            if (null != reactor)
                reactor.close();
        }
    }
}
