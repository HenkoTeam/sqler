package dev.henko.sqler.annotated.serializer;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeSerializer<T> {

  T deserialize(ResultSet resultSet, String colum) throws SQLException;

  Object serialize(T object);

  DataType dataType();

  TypeToken<T> type();
}
