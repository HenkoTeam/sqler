package dev.henko.sqler.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.henko.sqler.driver.DriverSource;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.Nullable;

public class MySQLConnection
    implements Connection {

  private final ConnectionCredentials properties;
  private Jdbi jdbi;

  public MySQLConnection(ConnectionCredentials properties) {
    this.properties = properties;
  }

  @Override
  public void connect(Object... ignored) {
    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(
        String.format(DriverSource.MySQL.getUrl(),
            properties.getHostname(), properties.getPort(), properties.getDatabase()
        )
    );

    config.setDriverClassName(DriverSource.MySQL.getClazz());

    config.setUsername(properties.getUsername());
    config.setPassword(properties.getPassword());

    config.setMaximumPoolSize(6);
    jdbi = Jdbi.create(new HikariDataSource(config));
  }

  @Override
  public @Nullable Jdbi get() {
    return jdbi;
  }
}
