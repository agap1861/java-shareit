package ru.practicum.shareit.excaption;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
