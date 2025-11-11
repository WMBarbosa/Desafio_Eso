package com.barbosa.desafio_tech.domain.service;

import com.barbosa.desafio_tech.domain.repository.TransactionRepository;
import com.barbosa.desafio_tech.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private static final int INITIAL_CREDITS = 10_000;

}
