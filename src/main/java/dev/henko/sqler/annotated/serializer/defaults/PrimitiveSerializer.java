package dev.henko.sqler.annotated.serializer.defaults;

import dev.henko.sqler.annotated.serializer.TypeSerializer;

public interface PrimitiveSerializer<T>
    extends TypeSerializer<T> {

  @Override
  default Object serialize(T object) {
    return object;
  }
}
