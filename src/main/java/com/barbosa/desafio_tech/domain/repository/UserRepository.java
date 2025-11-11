package com.barbosa.desafio_tech.domain.repository;

import com.barbosa.desafio_tech.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
