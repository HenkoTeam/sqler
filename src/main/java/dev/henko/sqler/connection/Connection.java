package dev.henko.sqler.connection;

import dev.henko.sqler.driver.DriverSource;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Connection {

  static @NotNull Connection create(
      @NotNull ConnectionProperties properties,
      @NotNull DriverSource source
  ) {
    return switch (source) {
      case MySQL -> new MySQLConnection(properties);
      case SQLite -> new SQLiteConnection(properties);
    };
  }

  void connect(Object... params);

  @Nullable
  Jdbi get();

}
