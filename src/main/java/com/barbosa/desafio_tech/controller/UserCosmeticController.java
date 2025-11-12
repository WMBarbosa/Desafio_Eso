package com.barbosa.desafio_tech.controller;

import com.barbosa.desafio_tech.domain.dto.UserCosmeticDTO;
import com.barbosa.desafio_tech.domain.service.UserCosmeticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/cosmetics")
@RequiredArgsConstructor
public class UserCosmeticController {

    private final UserCosmeticService userCosmeticService;

    @GetMapping
    public ResponseEntity<Page<UserCosmeticDTO>> getAllUserCosmetics(@PathVariable Long userId,
            Pageable pageable) {
        Page<UserCosmeticDTO> cosmetics = userCosmeticService.findAllByUserId(userId, pageable);
        return ResponseEntity.ok(cosmetics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCosmeticDTO> getUserCosmeticById(@PathVariable Long userId, @PathVariable Long id) {
        UserCosmeticDTO cosmetic = userCosmeticService.findById(id);
        return ResponseEntity.ok(cosmetic);
    }

    @GetMapping("/cosmetic/{cosmeticId}")
    public ResponseEntity<UserCosmeticDTO> getUserCosmeticByCosmeticId(@PathVariable Long userId,
            @PathVariable String cosmeticId) {
        UserCosmeticDTO cosmetic = userCosmeticService.findByUserIdAndCosmeticId(userId, cosmeticId);
        return ResponseEntity.ok(cosmetic);
    }

    @GetMapping("{/search}")
    public ResponseEntity<UserCosmeticDTO> findNameLike(@RequestParam String name, Pageable pageable) {
        Page<UserCosmeticDTO> cosmetics = userCosmeticService.findByFirstnameLike(name, pageable);
        return ResponseEntity.ok().body(cosmetics.getContent()
                .isEmpty() ? null : cosmetics.getContent()
                .get(0));
    }
}

