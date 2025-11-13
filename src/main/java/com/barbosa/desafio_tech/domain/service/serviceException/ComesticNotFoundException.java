package com.barbosa.desafio_tech.domain.service.serviceException;

public class ComesticNotFoundException extends RuntimeException{

    public ComesticNotFoundException(Long userId, String cosmeticId) {
        super(("Cosmético não encontrado para o usuário " + cosmeticId + userId));
    }

    public ComesticNotFoundException(String message) {
        super(message);
    }
}
