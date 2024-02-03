package com.example.platform.service;

import com.example.platform.aspect.Constant;
import com.example.platform.aspect.exception.NoSuchRecordException;
import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.RoleDTO;
import com.example.platform.mapper.RoleMapper;
import com.example.platform.model.ERole;
import com.example.platform.model.Role;
import com.example.platform.repository.RoleRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class RoleService implements Service<RoleDTO> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> findAll() {
        return null;
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.toDto(RecordGetter.getRecordFromTable(id, roleRepository));
    }

    @Override
    public RoleDTO create(RoleDTO roleDTO) {
        final Role role = roleMapper.toEntity(roleDTO);
        roleRepository.save(role);

        return roleMapper.toDto(role);
    }

    @Override
    public RoleDTO deleteById(Long id) {
        return null;
    }

    @Override
    public RoleDTO update(Long id, RoleDTO roleDTO) {
        return null;
    }

    public RoleDTO findByName(ERole name) {
        Optional<Role> role = roleRepository.findByName(name);
        return roleMapper.toDto(role.orElseThrow(() -> new NoSuchRecordException(Constant.RECORD_NOT_FOUND + name)));
    }
}