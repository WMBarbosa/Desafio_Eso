package com.barbosa.desafio_tech.domain.service.serviceException;

public class ActiveUserException extends RuntimeException {

    public ActiveUserException(String message) {
        super(message);
    }
}
