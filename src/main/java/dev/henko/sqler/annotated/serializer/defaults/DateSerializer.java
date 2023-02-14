package dev.henko.sqler.annotated.serializer.defaults;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.annotated.serializer.TypeSerializer;
import dev.henko.sqler.element.DataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DateSerializer
    implements TypeSerializer<Date> {

  @Override
  public Date deserialize(ResultSet resultSet, String colum) throws SQLException {
    return resultSet.getTimestamp(colum);
  }

  @Override
  public Object serialize(Date object) {
    return new Timestamp(object.getTime());
  }

  @Override
  public DataType dataType() {
    return DataType.TIMESTAMP;
  }

  @Override
  public TypeToken<Date> type() {
    return TypeToken.get(Date.class);
  }
}
