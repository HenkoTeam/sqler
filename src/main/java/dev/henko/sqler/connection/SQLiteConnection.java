package dev.henko.sqler.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.henko.sqler.driver.DriverSource;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.Nullable;

public class SQLiteConnection
    implements Connection {

  private final ConnectionProperties properties;
  private Jdbi jdbi;

  public SQLiteConnection(ConnectionProperties properties) {
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

    config.setMaximumPoolSize(properties.getMaximumConnections());
    config.setConnectionTimeout(properties.getConnectionTimeout());

    jdbi = Jdbi.create(new HikariDataSource(config));
  }

  @Override
  public @Nullable Jdbi get() {
    return jdbi;
  }
}
