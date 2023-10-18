package dev.henko.sqler.codec;

import org.jetbrains.annotations.NotNull;

public interface TypeCodec<M> {

    @NotNull CodecWriter serialize(
            final @NotNull M model
    );

    @NotNull M deserialize(
            final @NotNull CodecReader reader
    );


}
