package com.example.platform.dto;

import com.example.platform.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
