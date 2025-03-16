package com.philipe.demo.application.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.domains.model.UserEntity;

public class UserMapper {
    
    private static ModelMapper mapper = new ModelMapper();

    public static UserEntity toEntity(final UserDto dto){
        return mapper.map(dto, UserEntity.class);
    }

    public static UserDto toDto(final UserEntity entity){
        return mapper.map(entity, UserDto.class);
    }

   
}
