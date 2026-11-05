package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    Specialty mapToEntity(SpecialtyDTO dto);

    @Mapping(target = "hOpen", source = "HOpen")
    @Mapping(target = "hClose", source = "HClose")
    SpecialtyDTO mapToDto(Specialty entity);
}
