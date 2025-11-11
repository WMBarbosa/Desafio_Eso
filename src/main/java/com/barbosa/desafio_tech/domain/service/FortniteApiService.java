package com.barbosa.desafio_tech.domain.service;

import com.barbosa.desafio_tech.domain.dto.ComesticDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FortniteApiService {

    private final WebClient fortniteWebClient;

    public List<ComesticDTO> getAllCosmetics() {
        FortniteResponse response = fortniteWebClient
                .get()
                .uri("/cosmetics/br")
                .retrieve()
                .bodyToMono(FortniteResponse.class)
                .onErrorResume(ex -> Mono.just(new FortniteResponse()))
                .block();

        return mapCosmetics(response);
    }

    public List<ComesticDTO> getNewCosmetics() {
        FortniteResponse response = fortniteWebClient
                .get()
                .uri("/cosmetics/new")
                .retrieve()
                .bodyToMono(FortniteResponse.class)
                .onErrorResume(ex -> Mono.just(new FortniteResponse()))
                .block();

        return mapCosmetics(response);
    }

    public List<ComesticDTO> getShopItems() {
        FortniteResponse response = fortniteWebClient
                .get()
                .uri("/shop/br")
                .retrieve()
                .bodyToMono(FortniteResponse.class)
                .onErrorResume(ex -> Mono.just(new FortniteResponse()))
                .block();

        List<ComesticDTO> list = mapCosmetics(response);
        return list.stream().map(c -> {
            ComesticDTO d = ComesticDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .type(c.getType())
                    .rarity(c.getRarity())
                    .imageUrl(c.getImageUrl())
                    .price(c.getPrice())
                    .isNew(c.getIsNew())
                    .isOnSale(true)
                    .build();
            return d;
        }).collect(Collectors.toList());
    }

    public ComesticDTO getCosmeticById(String id) {
        Map response = fortniteWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cosmetics/br/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) return null;
        Object data = response.get("data");
        if (data instanceof Map dataMap) {
            return mapCosmetic(dataMap);
        }
        return null;
    }

    private List<ComesticDTO> mapCosmetics(FortniteResponse response) {
        if (response == null || response.data == null) return List.of();
        List<ComesticDTO> result = new ArrayList<>();
        for (Object obj : response.data) {
            if (obj instanceof Map map) {
                result.add(mapCosmetic(map));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private ComesticDTO mapCosmetic(Map item) {
        String id = Objects.toString(item.get("id"), null);
        String name = Objects.toString(item.get("name"), null);

        String type = null;
        Object typeObj = item.get("type");
        if (typeObj instanceof Map typeMap) {
            type = Objects.toString(typeMap.get("value"), null);
        } else if (typeObj != null) {
            type = typeObj.toString();
        }

        String rarity = null;
        Object rarityObj = item.get("rarity");
        if (rarityObj instanceof Map rMap) {
            rarity = Objects.toString(rMap.get("value"), null);
        } else if (rarityObj != null) {
            rarity = rarityObj.toString();
        }

        String imageUrl = null;
        Object imagesObj = item.get("images");
        if (imagesObj instanceof Map imgMap) {
            imageUrl = Objects.toString(imgMap.get("icon"), null);
            if (imageUrl == null) imageUrl = Objects.toString(imgMap.get("smallIcon"), null);
        }

        Integer price = null;
        Object shopHistory = item.get("shopHistory");
        Object priceObj = item.get("price");
        if (priceObj instanceof Number n) {
            price = n.intValue();
        } else if (shopHistory != null) {
            // quando vier da loja, às vezes o preço está em nested "regularPrice" / "finalPrice"
            Object priceMap = item.get("price");
            if (priceMap instanceof Map p) {
                Object finalPrice = p.get("finalPrice");
                if (finalPrice instanceof Number n2) price = n2.intValue();
            }
        }

        Boolean isNew = null;
        if (item.containsKey("new")) {
            Object v = item.get("new");
            if (v instanceof Boolean b) isNew = b; else isNew = Boolean.parseBoolean(v.toString());
        }

        Boolean isOnSale = null;
        if (item.containsKey("offerTag")) {
            isOnSale = true;
        }

        return ComesticDTO.builder()
                .id(id)
                .name(name)
                .type(type)
                .rarity(rarity)
                .imageUrl(imageUrl)
                .price(price)
                .isNew(isNew)
                .isOnSale(isOnSale)
                .build();
    }

    @Data
    private static class FortniteResponse {
        private Object status;
        private List<Object> data;
    }
}
