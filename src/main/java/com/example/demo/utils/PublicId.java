package com.example.demo.utils;

import lombok.experimental.UtilityClass;
import org.hashids.Hashids;

@UtilityClass
public class PublicId {

  private final String SECRET = EnvUtils.get("PUBLIC_ID_SECRET", "BAYE_FALL");
  private final Hashids hashids = new Hashids(SECRET, 9);

  public String encode(long id) {
    return hashids.encode(id);
  }

  public Long decode(String hash) {
    long[] decoded = hashids.decode(hash);
    if (decoded.length == 0) {
      throw new IllegalArgumentException("Invalid hash");
    }
    return decoded[0];
  }
}
