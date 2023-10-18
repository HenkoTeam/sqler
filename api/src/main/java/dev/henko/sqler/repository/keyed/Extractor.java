package dev.henko.sqler.repository.keyed;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Extractor<M> {

    @NotNull Object extract(final @NotNull M model);

}