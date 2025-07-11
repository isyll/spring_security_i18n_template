package com.example.demo.service;

import com.example.demo.constants.RedisKeys;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService extends BaseService {

  private final RedisTemplate<String, String> redisTemplate;

  public void blacklistToken(String token, long expirationMillis) {
    redisTemplate
        .opsForValue()
        .set(RedisKeys.jwtBlacklist(token), "true", expirationMillis, TimeUnit.MILLISECONDS);
  }

  public boolean isTokenBlacklisted(String token) {
    return redisTemplate.hasKey(RedisKeys.jwtBlacklist(token));
  }
}
