package com.example.demo.repository;

import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

  @Query("SELECT COUNT(u) FROM User u")
  long count();

  Optional<User> findByRegistrationNumber(String registrationNumber);

  boolean existsByRegistrationNumber(String registrationNumber);
}
