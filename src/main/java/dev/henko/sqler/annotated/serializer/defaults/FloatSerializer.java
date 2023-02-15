package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatSerializer
    implements PrimitiveSerializer<Float> {

  @Override
  public Float deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getFloat(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.DECIMAL;
  }

  @Override
  public TypeToken<Float> type() {
    return TypeToken.get(float.class);
  }
}
