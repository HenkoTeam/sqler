package dev.henko.sqler;

import dev.henko.sqler.connection.Connection;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultIterable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SqlerStorage<T> implements Storage<T> {

  private static final Executor DEFAULT_ASYNC_EXECUTOR = Executors.newFixedThreadPool(3);

  protected final Jdbi connection;
  protected final TableMapper<T> mapper;
  protected final Table table;
  protected final Executor executor;

  public SqlerStorage(
      Connection connection,
      TableMapper<T> mapper,
      Executor executor
  ) {
    this.connection = connection.get();
    this.mapper = mapper;
    this.table = mapper.getTable();
    this.executor = executor;
    table.createIfNotExists(this.connection);
  }

  public SqlerStorage(
      Connection connection,
      TableMapper<T> mapper
  ) {
    this(connection, mapper, DEFAULT_ASYNC_EXECUTOR);
  }

  @Override
  public @NotNull CompletableFuture<T> find(
      @NotNull String where,
      @NotNull String value,
      @NotNull Consumer<T> thenAccept
  ) {
    return CompletableFuture.supplyAsync(() -> {
      try (Handle handle = connection.open()) {
        T object =  handle
            .select(String.format("SELECT * FROM <TABLE> WHERE %s = :where", where))
            .define("TABLE", table.getName())
            .bind("where", value)
            .map(mapper)
            .findFirst()
            .orElse(null);

        if(object != null) {
          thenAccept.accept(object);
        }

        return object;
      }
    }, executor);
  }

  @Override
  public @NotNull CompletableFuture<Collection<T>> findAll(
      @NotNull Consumer<T> thenAccept
  ) {
    return CompletableFuture.supplyAsync(() -> {
      try (Handle handle = connection.open()) {
        Collection<T> objects = handle
            .select("SELECT * FROM <TABLE>")
            .define("TABLE", table.getName())
            .map(mapper)
            .collect(Collectors.toUnmodifiableList());

        for (T object : objects) {
          thenAccept.accept(object);
        }

        return objects;
      }
    }, executor);
  }

  @Override
  public @NotNull CompletableFuture<ResultIterable<T>> select(
      @NotNull String query
  ) {
    return CompletableFuture.supplyAsync(() -> {
      try (Handle handle = connection.open()) {
        return handle
            .select(query)
            .define("TABLE", table.getName())
            .map(mapper);
      }
    }, executor);
  }

  @Override
  public void update(@NotNull String query) {
    executor.execute(() -> {
      try (Handle handle = connection.open()) {
        handle
            .createUpdate(query)
            .define("TABLE", table.getName())
            .execute();
      }
    });
  }

  @Override
  public void update(@NotNull T object) {
    executor.execute(() -> {
      try (Handle handle = connection.open()) {
        handle
            .createUpdate("REPLACE INTO <TABLE> (<COLUMNS>) VALUES (<VALUES>)")
            .define("TABLE", table.getName())
            .define("COLUMNS", table.getColumns())
            .define("VALUES", table.getParameters())
            .bindMap(mapper.map(object))
            .execute();
      }
    });
  }

  @Override
  public void delete(@NotNull String where, @NotNull String value) {
    executor.execute(() -> {
      try (Handle handle = connection.open()) {
        handle
            .createUpdate(String.format("DELETE FROM <TABLE> WHERE %s = :where", where))
            .define("TABLE", table.getName())
            .bind("where", value)
            .execute();
      }
    });
  }
}
