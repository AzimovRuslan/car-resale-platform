package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.UserDTO;
import com.example.platform.mapper.UserMapper;
import com.example.platform.model.User;
import com.example.platform.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class UserService implements Service<UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return userMapper.toDto(RecordGetter.getRecordFromTable(id, userRepository));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {

        final User user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO deleteById(Long id) {

        User user = RecordGetter.getRecordFromTable(id, userRepository);

        if (user != null) {
            userRepository.deleteById(id);
        }

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {

        User user = RecordGetter.getRecordFromTable(id, userRepository);
        User userDetails = userMapper.toEntity(userDTO);

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setRoles(userDetails.getRoles());

        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
