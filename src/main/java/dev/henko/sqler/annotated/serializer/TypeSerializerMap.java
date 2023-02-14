package dev.henko.sqler.annotated.serializer;

import com.google.gson.reflect.TypeToken;
import dev.henko.sqler.annotated.error.ElementTypeSerializerNotFound;
import dev.henko.sqler.annotated.serializer.defaults.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TypeSerializerMap {

  private static final Map<TypeToken<?>, TypeSerializer<?>> SERIALIZERS = new ConcurrentHashMap<>();

  // register defaults
  static {
    register(new BooleanSerializer());
    register(new DateSerializer());
    register(new DoubleSerializer());
    register(new FloatSerializer());
    register(new IntegerSerializer());
    register(new StringSerializer());
    register(new UUIDSerializer());
    register(new ListSerializer<>(new TypeToken<List<UUID>>() {}));
    register(new ListSerializer<>(new TypeToken<List<String>>() {}));
  }

  public static <T> void register(TypeSerializer<T> serializer) {
    SERIALIZERS.put(
        serializer.type(),
        serializer
    );
  }

  public static TypeSerializer<?> get(TypeToken<?> typeToken) {
    TypeSerializer<?> serializer = SERIALIZERS.get(typeToken);
    if(serializer == null) {
      throw new ElementTypeSerializerNotFound();
    }
    return serializer;
  }
}
