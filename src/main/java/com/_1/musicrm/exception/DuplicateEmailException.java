// src/main/java/com/musicrm/exception/DuplicateEmailException.java
package com._1.musicrm.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}