package com.cerofour.MiniGram.user.domain.exception;

public class UsernameInvalidException extends RuntimeException {
    public UsernameInvalidException(String message) {
        super(message);
    }
    public UsernameInvalidException() {
      super("Invalid username");
    }
}
