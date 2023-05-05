package org.example.exceptions;

public class FilmAlreadyLikesException extends RuntimeException {

    public FilmAlreadyLikesException(String message) {
        super(message);
    }
}
