package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.GsonProvider;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ListSerializer<T>
    implements TypeSerializer<List<T>> {

  private final TypeToken<List<T>> typeToken;

  public ListSerializer(TypeToken<List<T>> typeToken) {
    this.typeToken = typeToken;
  }

  @Override
  public List<T> deserialize(ResultSet resultSet, String colum) throws SQLException {
    return GsonProvider.GSON.fromJson(resultSet.getString(colum), type().getType());
  }

  @Override
  public Object serialize(List<T> object) {
    return GsonProvider.GSON.toJson(object);
  }

  @Override
  public DataType dataType() {
    return DataType.LIST;
  }

  @Override
  public TypeToken<List<T>> type() {
    return typeToken;
  }
}
