package org.example.exceptions;

public class AlreadyUseException extends RuntimeException {

    public AlreadyUseException(String message) {
        super(message);
    }
}
