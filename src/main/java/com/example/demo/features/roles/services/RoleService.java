package com.example.demo.features.roles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.features.roles.models.Role;
import com.example.demo.features.roles.repository.RoleRepository;
import com.example.demo.i18n.I18nUtil;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private I18nUtil i18nUtil;

    public Role findRoleById(Long id) {
        Role role = roleRepository.findRoleById(id);
        if (role == null) {
            String message = i18nUtil.getMessage("error.role_not_found");
            throw new ResourceNotFoundException(message);
        }
        return role;
    }
}
