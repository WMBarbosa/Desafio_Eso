package com.barbosa.desafio_tech.domain.service.serviceException;

public class PriceInvalidException extends RuntimeException {

    public PriceInvalidException(String message) {
        super(message);
    }
}
