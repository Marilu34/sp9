package org.example.exceptions;

import lombok.AllArgsConstructor;

import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private final String error;
}