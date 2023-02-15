package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleSerializer
    implements PrimitiveSerializer<Double> {

  @Override
  public Double deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getDouble(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.DECIMAL;
  }

  @Override
  public TypeToken<Double> type() {
    return TypeToken.get(double.class);
  }
}
