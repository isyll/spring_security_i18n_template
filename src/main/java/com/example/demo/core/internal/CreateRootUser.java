package com.example.demo.core.internal;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.features.auth.models.User;
import com.example.demo.features.auth.repository.UserRepository;
import com.example.demo.features.roles.models.Role;
import com.example.demo.features.roles.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CreateRootUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final String rootEmail = "admin@example.com";

    @PostConstruct
    public void createRootUser() {
        if (userRepository.existsByEmail(rootEmail))
            return;
        try {
            userRepository.save(userData());
            System.out.println("Root user created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating root user: " + e.getMessage());
        }
    }

    private User userData() throws IOException {
        User user = new User();
        Role role = roleRepository.findByName("SUPERUSER");
        user.setEmail(rootEmail);
        user.setPassword(passwordEncoder.encode("P@ssword123"));
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setRoles(Set.of(role));
        user.setPhone("12345");

        return user;
    }

}
