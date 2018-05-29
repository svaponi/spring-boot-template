package io.github.svaponi;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.util.Arrays.asList;

/**
 * Similar to {@link org.springframework.context.annotation.Profile} but with inverted logic.
 * <p>
 * Example: {@code @ProfileNot({"testing", "mock"})}
 * <p>
 * Alternatively: {@code @ConditionalOnExpression("#{!environment.acceptsProfiles('testing', 'mock')}")}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ProfileNot.ConditionImpl.class)
public @interface ProfileNot {

    String[] value() default {};

    class ConditionImpl implements Condition {

        @Override
        public boolean matches(final ConditionContext conditionContext, final AnnotatedTypeMetadata annotatedTypeMetadata) {
            final String[] excludedProfiles = (String[]) annotatedTypeMetadata.getAnnotationAttributes(ProfileNot.class.getName()).get("value");
            final String[] activeProfiles = conditionContext.getEnvironment().getActiveProfiles();
            final boolean matches = asList(excludedProfiles).stream().anyMatch(p -> asList(activeProfiles).contains(p));
            return !matches;
        }
    }
}
