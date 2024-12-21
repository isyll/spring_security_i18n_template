package com.isyll.demo_app.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isyll.demo_app.dto.payload.request.UpdateUserRequest;
import com.isyll.demo_app.exceptions.UniqueConstraintViolationException;
import com.isyll.demo_app.exceptions.UserNotFoundException;
import com.isyll.demo_app.i18n.I18nUtil;
import com.isyll.demo_app.models.AccountStatus;
import com.isyll.demo_app.models.ERole;
import com.isyll.demo_app.models.Role;
import com.isyll.demo_app.models.User;
import com.isyll.demo_app.repository.RoleRepository;
import com.isyll.demo_app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    I18nUtil i18nUtil;

    public User createUser(User user) throws UniqueConstraintViolationException {
        checkUniqueConstraintsViolation(user);

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

    public User updateUser(User user, UpdateUserRequest dataRequest)
            throws UniqueConstraintViolationException {
        checkUniqueConstraintsViolation(user, dataRequest);

        if (dataRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    public void deleteMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                () -> new RuntimeException());

        user.setStatus(AccountStatus.DELETED);
        userRepository.save(user);
    }

    private void checkUniqueConstraintsViolation(User user) throws UniqueConstraintViolationException {
        String localizedMessage;
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.email_already_exists", user.getEmail());
            errors.put("email", localizedMessage);
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.username_already_exists", user.getUsername());
            errors.put("username", localizedMessage);
        }

        if (userRepository.existsByPhone(user.getPhone())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.phone_already_exists", user.getPhone());
            errors.put("phone", localizedMessage);
        }

        if (!errors.isEmpty()) {
            throw new UniqueConstraintViolationException(errors);
        }
    }

    private void checkUniqueConstraintsViolation(User user, UpdateUserRequest dataRequest)
            throws UniqueConstraintViolationException {
        String localizedMessage;
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByEmailAndNotExcludedUserId(user.getEmail(), user.getId())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.email_already_exists", user.getEmail());
            errors.put("email", localizedMessage);
        }

        if (userRepository.existsByUsernameAndNotExcludedUserId(user.getUsername(), user.getId())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.username_already_exists", user.getUsername());
            errors.put("username", localizedMessage);
        }

        if (userRepository.existsByPhoneAndNotExcludedUserId(user.getPhone().trim().replaceAll("\\s", ""),
                user.getId())) {
            localizedMessage = i18nUtil.getMessage(
                    "validation.phone_already_exists", user.getPhone());
            errors.put("phone", localizedMessage);
        }

        if (!errors.isEmpty()) {
            throw new UniqueConstraintViolationException(errors);
        }
    }
}
