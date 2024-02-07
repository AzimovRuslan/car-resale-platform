package com.example.platform;

import com.example.platform.dto.RoleDTO;

import com.example.platform.jwt.request.SignupRequest;
import com.example.platform.mapper.RoleMapper;
import com.example.platform.model.ERole;
import com.example.platform.service.AuthService;
import com.example.platform.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(RoleService roleService, RoleMapper roleMapper, AuthService authService) {
         return args -> {
             RoleDTO userRoleDTO = new RoleDTO();
             userRoleDTO.setName(ERole.USER);
             roleService.create(userRoleDTO);

             RoleDTO adminRoleDTO = new RoleDTO();
             adminRoleDTO.setName(ERole.ADMIN);
             roleService.create(adminRoleDTO);

             SignupRequest adminSR = new SignupRequest();
             Set<String> adminRoles = new HashSet<>();
             adminSR.setUsername("admin");
             adminSR.setPassword("admin");
             adminSR.setEmail("admin@gmail.com");
             adminRoles.add(roleService.findByName(userRoleDTO.getName()).getName().toString());
             adminRoles.add(roleService.findByName(adminRoleDTO.getName()).getName().toString());
             adminSR.setRoles(adminRoles);
             authService.signUp(adminSR);
         };
    }
}

