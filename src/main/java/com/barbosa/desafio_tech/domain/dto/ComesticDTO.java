package com.barbosa.desafio_tech.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComesticDTO {

    private String id;
    private String name;
    private String type;
    private String rarity;
    private String imageUrl;
    private Integer price;
    private Boolean isNew;
    private Boolean isOnSale;

}
