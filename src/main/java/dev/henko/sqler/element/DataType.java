package dev.henko.sqler.element;

public enum DataType {

  TIMESTAMP("TIMESTAMP"),
  BOOLEAN("TINYINT(1)"),
  TEXT("TEXT"),
  STRING("VARCHAR(255)"),
  NUMBER("INT"),
  DECIMAL("FLOAT"),
  EPOCH("INT(21)"),
  UUID("VARCHAR(36)");

  final String sql;

  DataType(String sql) {
    this.sql = sql;
  }

  public String toSql() {
    return sql;
  }
}