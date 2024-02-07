package com.example.platform.controller;

import com.example.platform.dto.RoleDTO;
import com.example.platform.model.Role;
import com.example.platform.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public Role create(@RequestBody RoleDTO roleDTO) {
        return roleService.create(roleDTO);
    }
}
