package com.example.demo.model;

import com.example.demo.utils.Base62;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PublicId {
  private final UUID uuid;

  public PublicId(UUID uuid) {
    this.uuid = uuid;
  }

  public static PublicId fromBase62(String encoded) {
    return new PublicId(Base62.decode(encoded));
  }

  @Override
  public String toString() {
    return Base62.encode(uuid);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PublicId other) {
      return uuid.equals(other.uuid);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }
}
