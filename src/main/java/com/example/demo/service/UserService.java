package com.example.demo.service;

import com.example.demo.core.payload.PaginationResponse;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
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

  public User findById(long id) {
    return userRepository.findById(id).orElse(null);
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
