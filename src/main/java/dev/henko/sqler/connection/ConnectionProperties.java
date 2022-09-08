package dev.henko.sqler.connection;

import java.util.Map;

public class ConnectionProperties {

  private final String hostname;
  private final String port;
  private final String database;
  private final String username;
  private final String password;

  private final int maximumConnections;
  private final int connectionTimeout;

  public ConnectionProperties(
      String hostname, String port,
      String database, String username,
      String password, int maximumConnections,
      int connectionTimeout
  ) {
    this.hostname = hostname;
    this.port = port;
    this.database = database;
    this.username = username;
    this.password = password;
    this.maximumConnections = maximumConnections;
    this.connectionTimeout = connectionTimeout;
  }

  public static ConnectionProperties of(Map<String, Object> map) {
    return new ConnectionProperties(
        (String) map.getOrDefault("hostname", "localhost"),
        (String) map.getOrDefault("port", 3306),
        (String) map.getOrDefault("database", "database"),
        (String) map.getOrDefault("username", "root"),
        (String) map.getOrDefault("password", "admin"),
        (Integer) map.getOrDefault("maximumConnections", 3),
        (Integer) map.getOrDefault("connectionTimeout", 30000)
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

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public int getMaximumConnections() {
    return maximumConnections;
  }

}
