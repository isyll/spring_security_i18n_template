package com.example.demo.service;

import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.dto.response.common.PaginatedResponse;
import com.example.demo.dto.search.UserLookup;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Base62;
import com.example.demo.utils.ReactivePaginationUtils;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final I18nUtil i18nUtil;

  public UserService(
      UserRepository userRepository, I18nUtil i18nUtil, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.i18nUtil = i18nUtil;
    this.passwordEncoder = passwordEncoder;
  }

  private boolean isUuid(String value) {
    return value != null
        && value.matches(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");
  }

  private ResourceNotFoundException notFound() {
    return new ResourceNotFoundException(i18nUtil.getMessage("error.user_not_found"));
  }

  public Mono<User> lookupUser(UserLookup lookup) {
    if (lookup.getId() != null) {
      UUID id =
          isUuid(lookup.getId()) ? UUID.fromString(lookup.getId()) : Base62.decode(lookup.getId());

      return userRepository.findById(id.toString()).switchIfEmpty(Mono.error(notFound()));
    }

    if (lookup.getEmail() != null) {
      return userRepository.findByEmail(lookup.getEmail()).switchIfEmpty(Mono.error(notFound()));
    }

    if (lookup.getPhone() != null) {
      return userRepository.findByPhone(lookup.getPhone()).switchIfEmpty(Mono.error(notFound()));
    }

    return Mono.error(
        new BadRequestException(i18nUtil.getMessage("error.no_unique_identifier_given")));
  }

  public Flux<User> findUsers(int page, int size) {
    return userRepository.findAll().skip((long) page * size).take(size);
  }

  public Mono<PaginatedResponse<User>> findUsers(PaginationParams params) {
    int page = params.getPage();
    int size = params.getSize();

    return ReactivePaginationUtils.paginate(
        userRepository.findAll(), userRepository.count(), page, size);
  }

  public Mono<User> registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
