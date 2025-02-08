package com.example.demo.core.initializers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.core.utils.PasswordGenerator;
import com.example.demo.features.roles.models.Role;
import com.example.demo.features.roles.repository.RoleRepository;
import com.example.demo.features.users.models.User;
import com.example.demo.features.users.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@DependsOn("authInitializer")
@Order(Ordered.LOWEST_PRECEDENCE)
public class CreateRootUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final String rootEmail = "admin@yaatal-school.com";
    private final String rootPassword = PasswordGenerator.generatePassword(8);

    @PostConstruct
    public void createRootUser() {

        try {
            if (userRepository.existsByEmail(rootEmail)) {
                // updateUser();
            } else {
                saveUser();
                log.info("Root user created successfully.\n");
                log.info("-- Email address : " + rootEmail);
                log.info("-- Password : " + rootPassword);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                e.printStackTrace();
            } else {
                log.error("Error creating root user: " + message);
            }
        }
    }

    // private void updateUser() {
    // User user = userRepository.findByEmail(rootEmail);
    // user.setPassword(passwordEncoder.encode(rootPassword));
    // userRepository.save(user);
    // }

    private void saveUser() {
        User user = new User();
        Role role = roleRepository.findByName("ROLE_SUPERUSER");
        user.setRoles(Set.of(role));
        user.setEmail(rootEmail);
        user.setPassword(passwordEncoder.encode(rootPassword));
        user.setFirstName("Root");
        user.setLastName("User");
        user.setPhone("12345");
        userRepository.save(user);
    }
}
