package dev.henko.sqler.annotated;

import dev.henko.sqler.Table;
import dev.henko.sqler.TableBuilder;
import dev.henko.sqler.annotated.annotation.Constraints;
import dev.henko.sqler.annotated.annotation.ForeignKey;
import dev.henko.sqler.annotated.annotation.Ignored;
import dev.henko.sqler.annotated.error.TableAnnotationNotFoundException;
import dev.henko.sqler.element.DataType;
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

    ElementBuilder builder = Element.builder(getElementType(field.getType()))
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

  private static DataType getElementType(Class<?> elementType) {
    return switch (elementType.getSimpleName()) {
      case "String" -> DataType.STRING;
      case "Integer", "int" -> DataType.NUMBER;
      case "Double", "double", "Float", "float" -> DataType.DECIMAL;
      case "Boolean, boolean" -> DataType.BOOLEAN;
      case "Date" -> DataType.TIMESTAMP;
      case "UUID" -> DataType.UUID;
      case "ArrayList", "LinkedList" -> DataType.LIST;
      default -> null;
    };

  }
}
