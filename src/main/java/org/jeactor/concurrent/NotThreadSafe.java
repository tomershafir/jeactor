package org.jeactor.concurrent;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documenting annotation to indicate a type (class, interface, annotation, enum) is not thread-safe.
 * 
 * <p>If applied to an abstract type, all implemetations should be non thread safe.
 * 
 * <p>The implementer must ensure non thread safety compatibility.
 */
@Retention(RetentionPolicy.CLASS)
@Documented
@Target(ElementType.TYPE)
public @interface NotThreadSafe {}
