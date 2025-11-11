package com.barbosa.desafio_tech.domain.repository;

import com.barbosa.desafio_tech.domain.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<Transaction> findByUserIdOrderByCreatedAtDesc(Long userId);
}

