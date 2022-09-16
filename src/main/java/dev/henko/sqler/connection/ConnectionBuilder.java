package dev.henko.sqler.connection;

import dev.henko.sqler.driver.DriverSource;

public class ConnectionBuilder {

  private final DriverSource source;

  private String hostname;
  private String port;
  private String database;
  private String username;
  private String password;

  ConnectionBuilder(DriverSource source) {
    this.source = source;
    this.hostname = "localhost";
    this.port =  "3306";
    this.database = "database";
    this.username = "root";
    this.password = "admin";
  }

  public ConnectionBuilder hostname(String hostname) {
    this.hostname = hostname;
    this.port = String.valueOf(port);
    return this;
  }

  public ConnectionBuilder port(int port) {
    this.port = String.valueOf(port);
    return this;
  }

  public ConnectionBuilder database(String database) {
    this.database = database;
    return this;
  }

  public ConnectionBuilder username(String username) {
    this.username = username;
    return this;
  }

  public ConnectionBuilder password(String password) {
    this.password = password;
    return this;
  }

  public Connection build() {
    return Connection.create(
        new ConnectionCredentials(
            hostname, port, database,
            username, password
        ),
        source
    );
  }
}
