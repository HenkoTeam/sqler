package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDSerializer
    implements TypeSerializer<UUID> {

  @Override
  public UUID deserialize(ResultSet resultSet, String colum) throws SQLException {
    return UUID.fromString(resultSet.getString(colum));
  }

  @Override
  public Object serialize(UUID object) {
    return object.toString();
  }

  @Override
  public DataType dataType() {
    return DataType.UUID;
  }

  @Override
  public TypeToken<UUID> type() {
    return TypeToken.get(UUID.class);
  }
}
