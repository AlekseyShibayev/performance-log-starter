package io.github.alekseyshibayev.infrastructure.log.performance.test.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shortcut to {@link ExtendWith @ExtendWith(MockitoExtension.class)} for Mockito unit tests.
 * <p>
 * Additional annotations for a Mockito unit test: {@code @InjectMocks}, {@code @Mock}, {@code @Spy}.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MockitoExtension.class)
public @interface MockitoUnitTest {

}
