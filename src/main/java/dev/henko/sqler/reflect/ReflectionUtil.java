package dev.henko.sqler.reflect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

final class ReflectionUtil {

  private static final Map<Class<?>, ResultFunction> TYPE_FUNCTIONS = new ConcurrentHashMap<>();
  private static final Gson GSON = new Gson();

  private static final TypeToken<List<String>> LIST_TYPE_TOKEN = new TypeToken<>() {};

  static {
    TYPE_FUNCTIONS.put(String.class, ResultSet::getString);
    TYPE_FUNCTIONS.put(Double.class, ResultSet::getDouble);
    TYPE_FUNCTIONS.put(Float.class, ResultSet::getFloat);
    TYPE_FUNCTIONS.put(Integer.class, ResultSet::getInt);
    TYPE_FUNCTIONS.put(Date.class, ResultSet::getTimestamp);
    TYPE_FUNCTIONS.put(UUID.class, (resultSet, colum) -> UUID.fromString(resultSet.getString(colum)));
    TYPE_FUNCTIONS.put(List.class, (resultSet, colum) -> GSON.fromJson(resultSet.getString(colum), LIST_TYPE_TOKEN.getType()));
  }

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
        Class<?> parameterType = List.class.isAssignableFrom(parameter.getType())
            ? List.class : parameter.getType();

        parametersInstances[i] = TYPE_FUNCTIONS.get(parameterType)
            .apply(resultSet, parameter.getName());
      }

      return (T) constructor.newInstance(parametersInstances);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  static Object getFieldValue(
      @NotNull Object o,
      @NotNull String fieldName
  ) {
    try {
      Field field = o.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);

      Object value = field.get(o);

      if(List.class.isAssignableFrom(field.getType())) {
        return GSON.toJson(value);
      }

      field.setAccessible(false);
      return value;
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @FunctionalInterface
  private interface ResultFunction {
    Object apply(ResultSet resultSet, String colum) throws SQLException;
  }
}
