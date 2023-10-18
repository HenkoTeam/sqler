package dev.henko.sqler.codec;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CodecWriter {

    private final Map<String, Object> values = new HashMap<>();

    private CodecWriter() {}

    @Contract(" -> new")
    public static @NotNull CodecWriter create() {
        return new CodecWriter();
    }

    @Contract("_, _ -> this")
    public @NotNull CodecWriter write(
            final @NotNull String key,
            final @NotNull Object value
    ) {
        values.put(key, value);
        return this;
    }

    @Contract("_, _ -> this")
    public @NotNull CodecWriter writeList(
            final @NotNull String key,
            final @NotNull List<?> value
    ) {
        values.put(key, value.toArray());
        return this;
    }

    public @NotNull Map<String, Object> write() {
        return values;
    }

}
