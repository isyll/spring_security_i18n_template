package com.example.demo.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RedisKeys {

  public final String JWT_BLACKLIST_PREFIX = "jwt:blacklist:";
  public final String PASSWORD_RESET_PREFIX = "reset:token:";

  public String jwtBlacklist(String token) {
    return JWT_BLACKLIST_PREFIX + token;
  }

  public String passwordResetToken(String uuid) {
    return PASSWORD_RESET_PREFIX + uuid;
  }
}
