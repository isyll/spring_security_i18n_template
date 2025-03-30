package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.i18n.I18nUtil;
import com.example.demo.models.Role;
import com.example.demo.repos.RoleRepository;

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
