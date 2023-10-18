package dev.henko.sqler.element;


public enum Constraint {

    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE"),
    PRIMARY("PRIMARY KEY"),
    SECONDARY("");

    final String sql;

    Constraint(String sql) {
        this.sql = sql;
    }

    public String toSql() {
        return sql;
    }
}