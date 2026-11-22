package com.company.app.infrastructure.log.performance.aop;

import com.company.app.infrastructure.log.performance.PerformanceLogConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * {@link PerformanceLogConfiguration}
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PerformanceLog {

}