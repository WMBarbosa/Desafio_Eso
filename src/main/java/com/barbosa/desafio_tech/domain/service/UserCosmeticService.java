package com.barbosa.desafio_tech.domain.service;

import com.barbosa.desafio_tech.domain.dto.UserCosmeticDTO;
import com.barbosa.desafio_tech.domain.entities.UserCosmetic;
import com.barbosa.desafio_tech.domain.mappers.UserCosmeticMapper;
import com.barbosa.desafio_tech.domain.repository.UserComesticRepository;
import com.barbosa.desafio_tech.domain.repository.UserRepository;
import com.barbosa.desafio_tech.domain.service.serviceException.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCosmeticService {

    private final UserComesticRepository userComesticRepository;
    private final UserRepository userRepository;
    private final UserCosmeticMapper userCosmeticMapper;

    @Transactional(readOnly = true)
    public Page<UserCosmeticDTO> findAllByUserId(Long userId, Pageable pageable) {
        loadUser(userId);
        return userComesticRepository.findByUserIdAndIsActiveTrue(userId, pageable)
                .map(userCosmeticMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<UserCosmeticDTO> findAllByUserId(Long userId) {
        loadUser(userId);
        return userComesticRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(userCosmeticMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserCosmeticDTO findById(Long id) {
        UserCosmetic userCosmetic = userComesticRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cosmético do usuário não encontrado com id: " + id));
        return userCosmeticMapper.toDto(userCosmetic);
    }

    @Transactional(readOnly = true)
    public UserCosmeticDTO findByUserIdAndCosmeticId(Long userId, String cosmeticId) {
        loadUser(userId);
        UserCosmetic userCosmetic = userComesticRepository
                .findByUserIdAndCosmeticIdAndIsActiveTrue(userId, cosmeticId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cosmético não encontrado para o usuário com id: " + userId + " e cosmeticId: " + cosmeticId));
        return userCosmeticMapper.toDto(userCosmetic);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndCosmeticId(Long userId, String cosmeticId) {
        return userComesticRepository.existsByUserIdAndCosmeticIdAndIsActiveTrue(userId, cosmeticId);
    }

    @Transactional(readOnly = true)
    public Page<UserCosmeticDTO> findByFirstnameLike(String name, Pageable pageable) {
        return userComesticRepository.findByFirstnameLike(name, pageable)
                .map(userCosmeticMapper::toDto);
    }

    private void loadUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}

