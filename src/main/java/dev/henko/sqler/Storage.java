package dev.henko.sqler;

import dev.henko.sqler.connection.Connection;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Storage<T> {

  private static final Executor DEFAULT_ASYNC_EXECUTOR = Executors.newFixedThreadPool(3);

  protected final Jdbi connection;

  protected final TableMapper<T> mapper;
  protected final Table table;

  protected final Executor executor;

  public Storage(
      Connection connection,
      TableMapper<T> mapper,
      Executor executor
  ) {
    this.connection = connection.get();
    this.mapper = mapper;
    this.table = mapper.getTable();
    this.executor = executor;
  }

  public Storage(
      Connection connection,
      TableMapper<T> mapper
  ) {
    this(connection, mapper, DEFAULT_ASYNC_EXECUTOR);
  }

  public CompletableFuture<T> find(String key, String value) {
    return CompletableFuture.supplyAsync(() -> {
      try (Handle handle = connection.open()) {
        return handle
            .select(String.format("SELECT * FROM <TABLE> WHERE %s = :%s", key))
            .define("TABLE", table.getName())
            .bind(key, value)
            .map(mapper)
            .findFirst()
            .orElse(null);
      }
    }, executor);
  }

  public void save(T object) {
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

  public void delete(String key, String value) {
    executor.execute(() -> {
      try (Handle handle = connection.open()) {
        handle
            .createUpdate(String.format("DELETE FROM <TABLE> WHERE %s = :%s", key))
            .define("TABLE", table.getName())
            .bind(key, value)
            .execute();
      }
    });
  }

}
