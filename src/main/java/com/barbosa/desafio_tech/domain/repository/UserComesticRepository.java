package com.barbosa.desafio_tech.domain.repository;

import com.barbosa.desafio_tech.domain.entities.UserCosmetic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserComesticRepository extends JpaRepository<UserCosmetic, Long> {

    List<UserCosmetic> findByUserIdAndIsActiveTrue(Long userId);

    Page<UserCosmetic> findByUserIdAndIsActiveTrue(Long userId, Pageable pageable);

    Optional<UserCosmetic> findByUserIdAndCosmeticIdAndIsActiveTrue(Long userId, String cosmeticId);

    boolean existsByUserIdAndCosmeticIdAndIsActiveTrue(Long userId, String cosmeticId);

    Page<UserCosmetic> findByFirstnameLike(String name, Pageable pageable);
}
