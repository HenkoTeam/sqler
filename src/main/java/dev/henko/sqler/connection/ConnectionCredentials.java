package dev.henko.sqler.connection;

import java.util.Map;

public class ConnectionCredentials {

  private final String hostname;
  private final String port;
  private final String database;
  private final String username;
  private final String password;

  public ConnectionCredentials(
      String hostname, String port,
      String database, String username,
      String password
  ) {
    this.hostname = hostname;
    this.port = port;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  public static ConnectionCredentials of(Map<String, Object> map) {
    return new ConnectionCredentials(
        (String) map.getOrDefault("hostname", "localhost"),
        (String) map.getOrDefault("port", "3306"),
        (String) map.getOrDefault("database", "database"),
        (String) map.getOrDefault("username", "root"),
        (String) map.getOrDefault("password", "admin")
    );
  }

  public String getHostname() {
    return hostname;
  }

  public String getPort() {
    return port;
  }

  public String getDatabase() {
    return database;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
