package dev.henko.sqler.annotated;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.Table;
import dev.henko.sqler.TableBuilder;
import dev.henko.sqler.annotated.annotation.Constraints;
import dev.henko.sqler.annotated.annotation.ForeignKey;
import dev.henko.sqler.annotated.annotation.Ignored;
import dev.henko.sqler.annotated.error.ElementTypeSerializerNotFound;
import dev.henko.sqler.annotated.error.TableAnnotationNotFoundException;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.annotated.serializer.TypeSerializerMap;
import dev.henko.sqler.element.Element;
import dev.henko.sqler.element.ElementBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

final class AnnotatedTableFactory {

  static @NotNull Table create(Class<?> type) {

    dev.henko.sqler.annotated.annotation.Table tableAnnotation = type
        .getDeclaredAnnotation(dev.henko.sqler.annotated.annotation.Table.class);

    if(tableAnnotation == null) {
      throw new TableAnnotationNotFoundException();
    }

    TableBuilder builder = Table.builder(tableAnnotation.value());

    for(Field field : type.getDeclaredFields()) {

      if(field.isAnnotationPresent(Ignored.class)) {
        continue;
      }

      builder.element(createElement(field));
    }

    return builder.build();
  }

  private static @NotNull Element createElement(Field field) {

    TypeSerializer<?> serializer = TypeSerializerMap.get(TypeToken.get(field.getGenericType()));

    ElementBuilder builder = Element.builder(serializer.dataType())
        .colum(field.getName());

    Constraints constraints = field.getDeclaredAnnotation(Constraints.class);

    if(constraints != null) {
      builder.constraints(constraints.value());
    }

    ForeignKey foreignKeyAnnotation = field.getDeclaredAnnotation(ForeignKey.class);

    if(foreignKeyAnnotation != null) {
      builder.foreignKey(Element.ForeignKey.of(
          foreignKeyAnnotation.tableReference(),
          foreignKeyAnnotation.elementReference(),
          foreignKeyAnnotation.trigger(),
          foreignKeyAnnotation.action()
      ));
    }

    return builder.build();
  }
}
