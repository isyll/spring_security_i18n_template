package com.example.demo.service;

import com.example.demo.constants.RedisKeys;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.lookup.UserByEmailService;
import com.example.demo.utils.Base64UrlHelper;
import com.example.demo.utils.EmailHelper;
import com.example.demo.utils.StringHelper;
import com.example.demo.utils.Translator;
import com.example.demo.utils.UrlBuilder;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetService extends BaseService {

  private final UserRepository userRepository;
  private final Translator translator;
  private final EmailHelper emailHelper;
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${RESET_PASSWORD_FRONTEND_BASE_URL}")
  private String resetPasswordBaseUrl;

  @Value("${RESET_PASSWORD_FRONTEND_PATH}")
  private String resetPasswordPath;

  @Value("${RESET_PASSWORD_EXPIRATION_MINUTES:15}")
  private long expirationMinutes;

  public void requestReset(String email) {
    User user =
        userByEmailService
            .findUserByEmail(email)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        translator.t("error.user_not_found_by_email", new Object[] {email})));

    UUID uuid = UUID.randomUUID();
    String redisKey = RedisKeys.passwordResetToken(user.getId().toString());
    if (redisTemplate.hasKey(redisKey)) {
      throw new BadRequestException(translator.t("error.password_reset_already_requested"));
    }

    redisTemplate.opsForValue().set(redisKey, uuid.toString(), expirationMinutes, TimeUnit.MINUTES);

    String resetLink = generateLink(uuid, user);
    emailHelper.sendPasswordResetEmail(email, resetLink, user.getPreferredLocale());
  }

  @Transactional
  public void confirmReset(String token, String newPassword, String email) {
    User user =
        userByEmailService
            .findUserByEmail(email)
            .orElseThrow(
                () -> new BadRequestException(translator.t("error.invalid_password_reset_token")));

    String redisKey = RedisKeys.passwordResetToken(user.getId().toString());
    String storedUuidStr = redisTemplate.opsForValue().get(redisKey);

    if (storedUuidStr == null) {
      throw new BadRequestException(translator.t("error.invalid_password_reset_token"));
    }

    UUID tokenUuid = extractResetUuidOrThrow(token);

    if (!tokenUuid.toString().equals(storedUuidStr)) {
      throw new BadRequestException(translator.t("error.invalid_password_reset_token"));
    }

    user.setPassword(newPassword);
    userRepository.save(user);
    redisTemplate.delete(redisKey);
  }

  private String generateToken(UUID uuid) {
    return Base64UrlHelper.encodeString(uuid.toString());
  }

  private String generateLink(UUID uuid, User user) {
    UrlBuilder urlBuilder = new UrlBuilder(resetPasswordBaseUrl);
    return urlBuilder.build(
        resetPasswordPath, Map.of("token", generateToken(uuid), "email", user.getEmail()));
  }

  private UUID extractResetUuidOrThrow(String token) {
    String uuidStr = Base64UrlHelper.decodeToString(token);
    if (!StringHelper.isValidUUID(uuidStr)) {
      throw new BadRequestException(translator.t("error.invalid_password_reset_token"));
    }
    return UUID.fromString(uuidStr);
  }
}
