package com.example.demo.repository;

import com.example.demo.model.UsedPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedPhoneRepository extends JpaRepository<UsedPhoneNumber, String> {}
