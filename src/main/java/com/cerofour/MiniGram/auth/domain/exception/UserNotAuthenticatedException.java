package com.cerofour.MiniGram.auth.domain.exception;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
    public UserNotAuthenticatedException() {
        super("Client is not logged in.");
    }
}
