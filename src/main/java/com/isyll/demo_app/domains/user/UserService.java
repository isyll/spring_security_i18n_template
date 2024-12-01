package com.isyll.demo_app.domains.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isyll.demo_app.common.exceptions.UniqueConstraintViolationException;
import com.isyll.demo_app.domains.user.models.User;
import com.isyll.demo_app.domains.user.repository.RoleRepository;
import com.isyll.demo_app.domains.user.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(final User user) throws UniqueConstraintViolationException, PasswordNotMatchingException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UniqueConstraintViolationException("email", "Email address '" + user.getEmail() + "' already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UniqueConstraintViolationException("username", "Username '" + user.getUsername() + "' already exists");
        }
        if (userRepository.existsByPhone(user.getPhone().trim().replaceAll("\\s", ""))) {
            throw new UniqueConstraintViolationException("phone", "Phone number '" + user.getPhone() + "' already exists");
        }

        if (!user.getPassword().equals(user.getPasswordConfirm()))
            throw new PasswordNotMatchingException();

        final Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        role.ifPresent((value) -> {
            final Set<Role> roles = new HashSet<>();
            roles.add(value);
            user.setRoles(roles);
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            return null;
        }
    }
}
