package com.example.demo.service;

import com.example.demo.config.i18n.I18nUtils;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.dto.search.UserLookup;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Base62;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final I18nUtils i18n;

  public UserService(
      UserRepository userRepository, I18nUtils i18n, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.i18n = i18n;
    this.passwordEncoder = passwordEncoder;
  }

  private boolean isUuid(String value) {
    return value != null
        && value.matches(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");
  }

  private ResourceNotFoundException notFound() {
    return new ResourceNotFoundException(i18n.getMessage("error.user_not_found"));
  }

  public User lookupUser(UserLookup lookup) {
    if (lookup.getId() != null) {
      UUID id =
          isUuid(lookup.getId()) ? UUID.fromString(lookup.getId()) : Base62.decode(lookup.getId());

      return userRepository.findById(id).orElseThrow(this::notFound);
    }

    if (lookup.getEmail() != null) {
      return Optional.ofNullable(userRepository.findByEmail(lookup.getEmail()))
          .orElseThrow(this::notFound);
    }

    if (lookup.getPhone() != null) {
      return Optional.ofNullable(userRepository.findByPhone(lookup.getPhone()))
          .orElseThrow(this::notFound);
    }

    throw new BadRequestException(i18n.getMessage("error.no_unique_identifier_given"));
  }

  public Page<User> findUsers(PaginationParams params) {
    return userRepository.findAll(params.getPageable());
  }

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
