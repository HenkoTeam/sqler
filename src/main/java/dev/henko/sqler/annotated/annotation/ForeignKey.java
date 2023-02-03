package dev.henko.sqler.annotated.annotation;

import dev.henko.sqler.element.Element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForeignKey {

  String tableReference();

  String elementReference();

  Element.ForeignKey.ForeignTrigger trigger();

  Element.ForeignKey.ForeignAction action();
}
