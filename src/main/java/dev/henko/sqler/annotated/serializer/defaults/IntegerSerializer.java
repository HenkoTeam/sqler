package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerSerializer
    implements PrimitiveSerializer<Integer> {

  @Override
  public Integer deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getInt(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.NUMBER;
  }

  @Override
  public TypeToken<Integer> type() {
    return TypeToken.get(int.class);
  }
}
