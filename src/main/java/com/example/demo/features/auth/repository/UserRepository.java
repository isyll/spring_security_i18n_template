package com.example.demo.features.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.features.auth.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        User findByEmail(String email);

        boolean existsByEmail(String email);

        boolean existsByPhone(String phone);

        @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.email = :email AND u.id <> :excludedUserId", nativeQuery = true)
        boolean existsByEmailAndNotExcludedUserId(@Param("email") String email,
                        @Param("excludedUserId") Long excludedUserId);

        @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.phone = :phone AND u.id <> :excludedUserId", nativeQuery = true)
        boolean existsByPhoneAndNotExcludedUserId(@Param("phone") String phone,
                        @Param("excludedUserId") Long excludedUserId);

}