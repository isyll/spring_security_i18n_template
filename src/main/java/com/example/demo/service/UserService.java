package com.example.demo.service;

import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.core.utils.Base62;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.search.UserLookup;
import com.example.demo.i18n.I18nUtil;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private I18nUtil i18nUtil;

  private boolean isUuid(String value) {
    return value != null
        && value.matches(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");
  }

  private ResourceNotFoundException notFound() {
    return new ResourceNotFoundException(i18nUtil.getMessage("error.user_not_found"));
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

    throw new BadRequestException(i18nUtil.getMessage("error.no_unique_identifier_given"));
  }

  public PaginationResponse<User> findUsers(PaginationParams params) {
    Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), params.getSort());
    Page<User> users = userRepository.findAll(pageable);
    return new PaginationResponse<>(users);
  }

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
