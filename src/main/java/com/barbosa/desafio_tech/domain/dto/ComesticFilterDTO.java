package com.barbosa.desafio_tech.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComesticFilterDTO {
    private String name;
    private String type;
    private String rarity;
    private Boolean isNew;
    private Boolean isOnSale;
}
