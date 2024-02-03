package com.example.platform.mapper;

import com.example.platform.aspect.utility.ModelMapperCreator;
import com.example.platform.dto.UserDTO;
import com.example.platform.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserDTO, User> {

    @Override
    public UserDTO toDto(User account) {

        return ModelMapperCreator.getModelMapper().map(account, UserDTO.class);
    }

    @Override
    public User toEntity(UserDTO accountDTO) {

        return ModelMapperCreator.getModelMapper().map(accountDTO, User.class);
    }
}
