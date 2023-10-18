package dev.henko.sqler.codec.error;

import java.sql.SQLException;

public class InvalidArrayTypeException extends SQLException {

    public InvalidArrayTypeException(String message) {
        super(message);
    }
}
