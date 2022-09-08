package dev.henko.sqler;

import org.jdbi.v3.core.mapper.RowMapper;

import java.util.Map;

public interface TableMapper<T>
    extends RowMapper<T> {

  Map<String, Object> map(T object);

}
