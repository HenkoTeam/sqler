package dev.henko.sqler.codec;

import com.google.common.collect.ImmutableMap;
import dev.henko.sqler.codec.error.InvalidArrayTypeException;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

public final class TypeParser {

    private static final Map<Integer, Class<?>> TYPES;

    static {
        TYPES = ImmutableMap.<Integer, Class<?>>builder()
                .put(Types.BOOLEAN, boolean.class)
                .put(Types.TINYINT, byte.class)
                .put(Types.SMALLINT, short.class)
                .put(Types.INTEGER, int.class)
                .put(Types.BIGINT, long.class)
                .put(Types.FLOAT, float.class)
                .put(Types.REAL, float.class)
                .put(Types.DOUBLE, double.class)
                .put(Types.NUMERIC, double.class)
                .put(Types.DECIMAL, double.class)
                .put(Types.CHAR, char.class)
                .put(Types.VARCHAR, String.class)
                .put(Types.LONGVARCHAR, String.class)
                .put(Types.DATE, Date.class)
                .put(Types.TIME, Time.class)
                .put(Types.TIMESTAMP, Timestamp.class)
                .put(Types.NCHAR, String.class)
                .put(Types.NVARCHAR, String.class)
                .put(Types.LONGNVARCHAR, String.class)
                .put(Types.NCLOB, String.class)
                .put(Types.TIME_WITH_TIMEZONE, Time.class)
                .put(Types.TIMESTAMP_WITH_TIMEZONE, Timestamp.class)
                .build();
    }

    private TypeParser() {}

    public static @NotNull Class<?> parse(
            final int typeCode
    ) throws InvalidArrayTypeException {

        final var type = TYPES.get(typeCode);

        if (type == null) {
            throw new InvalidArrayTypeException(
                    "Type code " + typeCode + " is not supported"
            );
        }

        return type;
    }

}
