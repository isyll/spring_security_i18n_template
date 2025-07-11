package com.example.demo.service;

import com.example.demo.dto.filter.UserLookup;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService {

  private final UserRepository userRepository;
  private final Translator translator;

  private ResourceNotFoundException notFound() {
    return new ResourceNotFoundException(translator.t("error.user_not_found"));
  }

  public User lookup(UserLookup lookup) {
    throw new BadRequestException(translator.t("error.no_unique_identifier_given"));
  }

  public Page<User> findUsers(PaginationParams params) {
    return userRepository.findAll(params.toPageable());
  }
}
