package com.example.demo.repository;

import com.example.demo.model.UsedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedEmailRepository extends JpaRepository<UsedEmail, String> {}
