package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LongSerializer
    implements PrimitiveSerializer<Long> {

  @Override
  public Long deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getLong(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.LONG;
  }

  @Override
  public TypeToken<Long> type() {
    return TypeToken.get(long.class);
  }
}
