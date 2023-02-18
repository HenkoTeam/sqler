package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumSerializer<T extends Enum<T>>
    implements TypeSerializer<T> {

  private final Class<T> enumType;

  public EnumSerializer(Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  public T deserialize(ResultSet resultSet, String colum) throws SQLException {
    return Enum.valueOf(enumType, resultSet.getString(colum));
  }

  @Override
  public Object serialize(T object) {
    return object.name();
  }

  @Override
  public DataType dataType() {
    return DataType.STRING;
  }

  @Override
  public TypeToken<T> type() {
    return TypeToken.get(enumType);
  }
}
