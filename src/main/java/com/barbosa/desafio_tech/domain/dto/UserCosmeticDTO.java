package com.barbosa.desafio_tech.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCosmeticDTO {
    private Long id;
    private String cosmeticId;
    private String cosmeticName;
    private Integer price;
    private String rarity;
    private Boolean isActive;
    private Long userId;
}

