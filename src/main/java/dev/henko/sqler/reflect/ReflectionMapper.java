package dev.henko.sqler.reflect;

import dev.henko.sqler.Table;
import dev.henko.sqler.element.Element;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionMapper {

  @NotNull
  public static Map<String, Object> map(
      @NotNull Table table,
      @NotNull Object object
  ) {
    Map<String, Object> map = new HashMap<>();
    for (Element element : table.getElements()) {
      map.put(element.getColumn(),
          ReflectionUtil.getFieldValue(object, element.getColumn())
      );
    }
    return map;
  }

  @NotNull
  public static <T> T map(
      @NotNull Class<T> type,
      @NotNull ResultSet resultSet
  ) throws SQLException {
    return ReflectionUtil.createInstance(type, resultSet);
  }

}
