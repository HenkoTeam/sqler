package dev.henko.sqler.driver;

import java.io.File;

public enum DriverSource {

  SQLite(
            "jdbc:sqlite:%s" + File.separator + "%s.db",
            "org.sqlite.JDBC"
  ),
  MySQL(
            "jdbc:mysql://%s:%s/%s",
                "com.mysql.cj.jdbc.Driver"
  );

  private final String url;

  private final String clazz;

  DriverSource(String url, String clazz) {
    this.url = url;
    this.clazz = clazz;
  }

  public String getUrl() {
    return url;
  }

  public String getClazz() {
    return clazz;
  }
}
