package com.example.platform.mapper;

import com.example.platform.aspect.utility.ModelMapperCreator;
import com.example.platform.dto.RoleDTO;
import com.example.platform.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper <RoleDTO, Role>{

    @Override
    public RoleDTO toDto(Role role) {

        return ModelMapperCreator.getModelMapper().map(role, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {

        return ModelMapperCreator.getModelMapper().map(roleDTO, Role.class);
    }
}
