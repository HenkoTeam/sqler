package dev.henko.sqler.repository.keyed;

import org.jetbrains.annotations.NotNull;

public record Key<M>(
        String field,
        Extractor<M> extractor
) {

    public @NotNull Object extract(M model) {
        return extractor.extract(model);
    }

}