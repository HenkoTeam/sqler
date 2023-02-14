package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanSerializer
    implements PrimitiveSerializer<Boolean> {

  @Override
  public Boolean deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getBoolean(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.BOOLEAN;
  }

  @Override
  public TypeToken<Boolean> type() {
    return TypeToken.get(Boolean.class);
  }
}
