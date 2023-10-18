package dev.henko.sqler.repository;

import com.google.common.collect.Table;
import dev.henko.sqler.repository.keyed.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UniqueKeyHashRepository<M>
        implements Repository<M> {

    private final Set<Key<M>> keys;
    private final Map<Integer, M> models;
    private final Table<String, Integer, Integer> hashesTable;


    public UniqueKeyHashRepository(
            final Map<Integer, M> models,
            final Map<Integer, Integer> hashes
    ) {
        this.models = models;
        this.hashes = hashes;
    }

    @Override
    public @NotNull List<M> find(
            final @NotNull String field,
            final @NotNull Object value
    ) {

        final var model = findFirst(field, value);

        if (model != null) {
            return List.of(model);
        }

        return List.of();
    }

    @Override
    public @Nullable M findFirst(
            final @NotNull String field,
            final @NotNull Object value
    ) {
        final var hashKey = hashesTable.get(
                field,
                value.hashCode()
        );
        if (hashKey != null) {
            return models.get(hashKey);
        }
        return null;
    }

    @Override
    public void save(final @NotNull M model) {
        var firstHash = -1;
        for (final var key : keys) {
            final var hash = key.extract(model)
                    .hashCode();
            if (firstHash == -1) {
                firstHash = hash;
            }
            hashesTable.put(key.field, hash, firstHash);
        }
        models.put(firstHash, model);
    }

}
