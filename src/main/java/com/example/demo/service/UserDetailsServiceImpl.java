package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.lookup.UserByEmailService;
import com.example.demo.utils.StringHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserByEmailService userByEmailService;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    if (StringHelper.isValidEmail(identifier)) {
      return userByEmailService
          .findUserByEmail(identifier)
          .orElseThrow(
              () -> new UsernameNotFoundException("User not found with email: " + identifier));
    }

    return userRepository
        .findByRegistrationNumber(identifier)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    "User not found with registration number: " + identifier));
  }
}
