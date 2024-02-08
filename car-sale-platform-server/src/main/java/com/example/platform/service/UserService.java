package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.UserDTO;
import com.example.platform.mapper.UserMapper;
import com.example.platform.model.User;
import com.example.platform.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class UserService implements Service<User, UserDTO> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll(PageRequest pq) {
        return userRepository.findAll(pq).getContent();
    }

    @Override
    public User findById(Long id) {
        return RecordGetter.getRecordFromTable(id, userRepository);
    }

    @Override
    public User create(UserDTO userDTO) {
        final User user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        return user;
    }

    @Override
    public User deleteById(Long id) {
        User user = RecordGetter.getRecordFromTable(id, userRepository);

        if (user != null) {
            userRepository.deleteById(id);
        }

        return user;
    }

    @Override
    public User update(Long id, UserDTO userDTO) {
        User user = RecordGetter.getRecordFromTable(id, userRepository);
        User userDetails = userMapper.toEntity(userDTO);

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setRoles(userDetails.getRoles());

        userRepository.save(user);

        return user;
    }
}
