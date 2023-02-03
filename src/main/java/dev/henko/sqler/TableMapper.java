package dev.henko.sqler;

import dev.henko.sqler.annotated.AnnotatedTableMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TableMapper<T>
    extends RowMapper<T> {

  Map<String, Object> map(T object);

  Table getTable();

  static <U> @NotNull TableMapper<U> create(@NotNull Class<U> type) {
    return new AnnotatedTableMapper<>(type);
  }

}
