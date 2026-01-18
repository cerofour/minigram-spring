package com.cerofour.MiniGram.user_follows.domain.exception;

public class InvalidFollowException extends RuntimeException {
    public InvalidFollowException(String message) {
        super(message);
    }
}
