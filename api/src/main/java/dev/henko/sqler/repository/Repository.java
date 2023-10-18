package dev.henko.sqler.repository;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Repository<M> {

    @Contract("_, _ -> new")
    @NotNull List<M> find(
            final @NotNull String field,
            final @NotNull Object value
    );

    @Nullable M findFirst(
            final @NotNull String field,
            final @NotNull Object value
    );

    void save(final @NotNull M model);

}
