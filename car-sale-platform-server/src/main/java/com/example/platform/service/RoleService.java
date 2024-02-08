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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class RoleService implements Service<Role, RoleDTO> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<Role> findAll(PageRequest pq) {
        return roleRepository.findAll(pq).getContent();
    }

    @Override
    public Role findById(Long id) {
        return RecordGetter.getRecordFromTable(id, roleRepository);
    }

    @Override
    public Role create(RoleDTO roleDTO) {
        final Role role = roleMapper.toEntity(roleDTO);
        roleRepository.save(role);

        return role;
    }

    @Override
    public Role deleteById(Long id) {
        Role role = RecordGetter.getRecordFromTable(id, roleRepository);

        if (role != null) {
            roleRepository.deleteById(id);
        }

        return role;
    }

    @Override
    public Role update(Long id, RoleDTO roleDTO) {
        Role role = RecordGetter.getRecordFromTable(id, roleRepository);
        Role roleDetails = roleMapper.toEntity(roleDTO);

        role.setName(roleDetails.getName());

        roleRepository.save(role);

        return role;
    }

    public Role findByName(ERole name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElseThrow(() -> new NoSuchRecordException(Constant.RECORD_NOT_FOUND + name));
    }
}
