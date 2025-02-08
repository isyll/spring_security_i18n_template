package com.example.demo.features.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.example.demo.core.payload.PaginationResponse;
import com.example.demo.features.users.models.User;
import com.example.demo.features.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public PaginationResponse<User> findUsers(int page, int size, String[] sortParams) {
        Sort sorting = Sort.by(Order.asc(sortParams[0]));
        if (sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])) {
            sorting = Sort.by(Order.desc(sortParams[0]));
        }
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<User> users = userRepository.findAll(pageable);
        return new PaginationResponse<>(users);
    }
}
