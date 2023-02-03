package dev.henko.sqler.annotated.annotation;

import dev.henko.sqler.element.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Constraints {
  Constraint[] value() default {};
}