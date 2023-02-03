package dev.henko.sqler.annotated;

import dev.henko.sqler.Table;
import dev.henko.sqler.TableMapper;
import dev.henko.sqler.reflect.ReflectionMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public final class AnnotatedTableMapper<T> implements TableMapper<T> {

  private final Class<T> type;
  private final Table table;

  public AnnotatedTableMapper(Class<T> type) {
    this.type = type;
    this.table = AnnotatedTableFactory.create(type);
  }


  @Override
  public Map<String, Object> map(T object) {
    return ReflectionMapper.map(table, object);
  }

  @Override
  public Table getTable() {
    return table;
  }

  @Override
  public T map(ResultSet rs, StatementContext ctx) throws SQLException {
    return ReflectionMapper.map(type, rs);
  }
}
