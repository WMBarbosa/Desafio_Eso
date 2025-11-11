package com.barbosa.desafio_tech.domain.service;

import com.barbosa.desafio_tech.domain.dto.ComesticDTO;
import com.barbosa.desafio_tech.domain.dto.ComesticFilterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComesticService {

    private final FortniteApiService fortniteApiService;

    public Page<ComesticDTO> listAll(Pageable pageable, ComesticFilterDTO filter) {
        List<ComesticDTO> all = fortniteApiService.getAllCosmetics();
        List<ComesticDTO> filtered = applyFilter(all, filter);
        return paginate(filtered, pageable);
    }

    public Page<ComesticDTO> listNew(Pageable pageable) {
        List<ComesticDTO> list = fortniteApiService.getNewCosmetics();
        return paginate(list, pageable);
    }

    public Page<ComesticDTO> listShop(Pageable pageable) {
        List<ComesticDTO> list = fortniteApiService.getShopItems();
        return paginate(list, pageable);
    }

    public ComesticDTO getById(String id) {
        return fortniteApiService.getCosmeticById(id);
    }

    private List<ComesticDTO> applyFilter(List<ComesticDTO> list, ComesticFilterDTO comestic) {
        if (comestic == null) return list;
        return list.stream()
                .filter(c -> comestic.getName() == null || containsIgnoreCase(c.getName(), comestic.getName()))
                .filter(c -> comestic.getType() == null || Objects.equals(c.getType(), comestic.getType()))
                .filter(c -> comestic.getRarity() == null || Objects.equals(c.getRarity(), comestic.getRarity()))
                .filter(c -> comestic.getIsNew() == null || Objects.equals(c.getIsNew(), comestic.getIsNew()))
                .filter(c -> comestic.getIsOnSale() == null || Objects.equals(c.getIsOnSale(), comestic.getIsOnSale()))
                .sorted(Comparator.comparing(ComesticDTO::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
                .collect(Collectors.toList());
    }

    private boolean containsIgnoreCase(String text, String token) {
        if (text == null || token == null) return false;
        return text.toLowerCase(Locale.ROOT).contains(token.toLowerCase(Locale.ROOT));
    }

    private Page<ComesticDTO> paginate(List<ComesticDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<ComesticDTO> content = list.subList(Math.min(start, end), end);
        return new PageImpl<>(content, pageable, list.size());
    }
}

