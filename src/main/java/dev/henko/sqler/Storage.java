package dev.henko.sqler;

import dev.henko.sqler.connection.Connection;
import org.jdbi.v3.core.result.ResultIterable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public interface Storage<T> {

  default @NotNull CompletableFuture<T> find(
      @NotNull String where,
      @NotNull String value
  ) {
    return find(where, value, o -> {});
  }

  @NotNull CompletableFuture<T> find(
      @NotNull String where,
      @NotNull String value,
      @NotNull Consumer<T> thenAccept
  );

  default @NotNull CompletableFuture<Collection<T>> findAll() {
    return findAll(o -> {});
  }

  @NotNull CompletableFuture<Collection<T>> findAll(
      @NotNull Consumer<T> thenAccept
  );

  @NotNull CompletableFuture<ResultIterable<T> >select(
      @NotNull String query
  );

  void update(@NotNull String query);

  void update(@NotNull T object);

  void delete(@NotNull String where, @NotNull String value);

  static <U> @NotNull Storage<U> create(
      @NotNull Class<U> type,
      @NotNull Connection connection,
      @NotNull Executor executor
  ) {
    return new SqlerStorage<>(connection, TableMapper.create(type), executor);
  }

  static <U> @NotNull Storage<U> create(
      @NotNull Class<U> type,
      @NotNull Connection connection
  ) {
    return new SqlerStorage<>(connection, TableMapper.create(type));
  }

}
