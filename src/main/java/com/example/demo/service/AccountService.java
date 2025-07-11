package com.example.demo.service;

import com.example.demo.dto.auth.ChangePasswordRequest;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService {

  private final PasswordEncoder passwordEncoder;
  private final Translator translator;
  private final UserRepository userRepository;

  public void changePassword(ChangePasswordRequest request) {
    User user = currentUser();
    if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
      throw new BadRequestException(translator.t("error.password_mismatch"));
    }

    user.setPassword(passwordEncoder.encode(request.newPassword()));
    user.setMustChangePassword(false);
    userRepository.save(user);
  }
}
