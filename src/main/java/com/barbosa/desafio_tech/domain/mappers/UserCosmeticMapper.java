package com.barbosa.desafio_tech.domain.mappers;

import com.barbosa.desafio_tech.domain.dto.UserCosmeticDTO;
import com.barbosa.desafio_tech.domain.entities.UserCosmetic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCosmeticMapper {

    @Mapping(source = "user.id", target = "userId")
    UserCosmeticDTO toDto(UserCosmetic userCosmetic);

    @Mapping(target = "user", ignore = true)
    UserCosmetic toEntity(UserCosmeticDTO userCosmeticDTO);
}

