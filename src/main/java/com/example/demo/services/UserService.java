package com.example.demo.services;

import com.example.demo.core.payload.PaginationResponse;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public PaginationResponse<User> findUsers(int page, int size, String[] sortParams) {
    Sort sorting = Sort.by(Order.asc(sortParams[0]));
    if (sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])) {
      sorting = Sort.by(Order.desc(sortParams[0]));
    }
    Pageable pageable = PageRequest.of(page, size, sorting);
    Page<User> users = userRepository.findAll(pageable);
    return new PaginationResponse<>(users);
  }

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
