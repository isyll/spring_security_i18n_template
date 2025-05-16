package com.example.demo.repository;

import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  Optional<User> findById(Long id);

  <S extends User> S save(S entity);

  User findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);

  @Query(
      value =
          "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.email = :email AND u.id <> :excludedUserId",
      nativeQuery = true)
  boolean existsByEmailAndNotExcludedUserId(
      @Param("email") String email, @Param("excludedUserId") Long excludedUserId);

  @Query(
      value =
          "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.phone = :phone AND u.id <> :excludedUserId",
      nativeQuery = true)
  boolean existsByPhoneAndNotExcludedUserId(
      @Param("phone") String phone, @Param("excludedUserId") Long excludedUserId);
}
