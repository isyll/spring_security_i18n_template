package com.example.demo.features.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.core.exceptions.BadRefreshTokenException;
import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.exceptions.InvalidTokenException;
import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.features.auth.models.AccountStatus;
import com.example.demo.features.auth.models.User;
import com.example.demo.features.auth.repository.UserRepository;
import com.example.demo.i18n.I18nUtil;
import com.example.demo.security.jwt.JwtUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            String message = i18nUtil.getMessage("error.user_not_found");
            throw new ResourceNotFoundException(message);
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

        if (!jwtUtils.checkTokenType(refreshToken, "refresh")) {
            throw new BadRefreshTokenException();
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

}
