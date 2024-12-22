package com.isyll.agrotrade.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isyll.agrotrade.dto.payload.request.UpdateUserRequest;
import com.isyll.agrotrade.exceptions.BadRequestException;
import com.isyll.agrotrade.exceptions.InvalidTokenException;
import com.isyll.agrotrade.exceptions.UniqueConstraintViolationException;
import com.isyll.agrotrade.exceptions.UserNotFoundException;
import com.isyll.agrotrade.i18n.I18nUtil;
import com.isyll.agrotrade.models.AccountStatus;
import com.isyll.agrotrade.models.ERole;
import com.isyll.agrotrade.models.Role;
import com.isyll.agrotrade.models.User;
import com.isyll.agrotrade.repository.RoleRepository;
import com.isyll.agrotrade.repository.UserRepository;
import com.isyll.agrotrade.security.jwt.JwtUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

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

    public String generateAccessToken(String username, String password) {
        Authentication authentication = generateAuthentication(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateAccessToken(authentication);
    }

    public String generateRefreshToken(String username, String password) {
        Authentication authentication = generateAuthentication(username, password);
        return jwtUtils.generateRefreshToken(authentication);
    }

    public String generateFromRefreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BadRequestException();
        }

        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateAccessToken(authentication);
    }

    private Authentication generateAuthentication(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
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
