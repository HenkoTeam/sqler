package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.element.DataType;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringSerializer
    implements PrimitiveSerializer<String> {

  @Override
  public String deserialize(@NotNull ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getString(colum);
  }

  @Override
  public DataType dataType() {
    return DataType.STRING;
  }

  @Override
  public TypeToken<String> type() {
    return TypeToken.get(String.class);
  }
}
