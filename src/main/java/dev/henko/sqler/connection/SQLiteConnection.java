package dev.henko.sqler.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.henko.sqler.driver.DriverSource;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.Nullable;

public class SQLiteConnection
    implements Connection {

  private final ConnectionCredentials properties;
  private Jdbi jdbi;

  public SQLiteConnection(ConnectionCredentials properties) {
    this.properties = properties;
  }

  @Override
  public void connect(Object... params) {
    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(
        String.format(DriverSource.SQLite.getUrl(),
            params[0], properties.getDatabase()
        )
    );

    config.setDriverClassName(DriverSource.SQLite.getClazz());

    config.setMaximumPoolSize(6);

    jdbi = Jdbi.create(new HikariDataSource(config));
  }

  @Override
  public @Nullable Jdbi get() {
    return jdbi;
  }
}
