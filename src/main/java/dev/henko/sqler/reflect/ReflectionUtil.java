package dev.henko.sqler.reflect;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.annotated.serializer.TypeSerializerMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;

final class ReflectionUtil {

  @NotNull
  @SuppressWarnings("unchecked")
  static <T> T createInstance(
      @NotNull Class<T> type,
      @NotNull ResultSet resultSet
  ) throws SQLException {
    try {
      Constructor<?> constructor = type.getDeclaredConstructors()[0];
      Parameter[] parameters = constructor.getParameters();
      Object[] parametersInstances = new Object[parameters.length];

      for (int i = 0; i < parameters.length; i++) {

        Parameter parameter = parameters[i];
        TypeSerializer<?> serializer = TypeSerializerMap
            .get(TypeToken.get(parameter.getParameterizedType()));

        parametersInstances[i] = serializer.deserialize(resultSet, parameter.getName());
      }

      return (T) constructor.newInstance(parametersInstances);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @NotNull
  static <T> Object getFieldValue(
      @NotNull Object o,
      @NotNull String fieldName
  ) {
    try {

      Field field = o.getClass().getDeclaredField(fieldName);

      TypeSerializer<T> serializer = (TypeSerializer<T>) TypeSerializerMap
          .get(TypeToken.get(field.getGenericType()));

      field.setAccessible(true);
      Object value = field.get(o);
      field.setAccessible(false);

      return serializer.serialize((T) value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
