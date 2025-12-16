package com.cerofour.MiniGram.auth.application.out;

public interface EncryptionPort {
    String encrypt(String plain);
    Boolean equals(String plain, String encrypted);
}
