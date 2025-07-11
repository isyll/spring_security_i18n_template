package com.example.demo.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Base62 {

  private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private final int BASE = ALPHABET.length();

  public boolean isInvalidBase62Uuid(String input) {
    return input == null || input.length() != 22 || !input.matches("^[A-Za-z0-9]+$");
  }

  public String encode(UUID uuid) {
    return encode(toBytes(uuid));
  }

  public String encode(byte[] data) {
    BigInteger bi = new BigInteger(1, data); // Unsigned
    StringBuilder sb = new StringBuilder();
    while (bi.compareTo(BigInteger.ZERO) > 0) {
      BigInteger[] divMod = bi.divideAndRemainder(BigInteger.valueOf(BASE));
      sb.append(ALPHABET.charAt(divMod[1].intValue()));
      bi = divMod[0];
    }
    return sb.reverse().toString();
  }

  public UUID decodeUuid(String encoded) {
    byte[] bytes = decodeToBytes(encoded);
    return fromBytes(bytes);
  }

  private static byte[] toBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.allocate(16);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  private static UUID fromBytes(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    long high = bb.getLong();
    long low = bb.getLong();
    return new UUID(high, low);
  }

  private static byte[] decodeToBytes(String encoded) {
    BigInteger bi = BigInteger.ZERO;
    for (char c : encoded.toCharArray()) {
      int index = ALPHABET.indexOf(c);
      if (index == -1) {
        throw new IllegalArgumentException("Invalid character in base62: " + c);
      }
      bi = bi.multiply(BigInteger.valueOf(BASE)).add(BigInteger.valueOf(index));
    }

    byte[] raw = bi.toByteArray();

    // Ensure exactly 16 bytes (UUID is 128 bits)
    if (raw.length == 16) {
      return raw;
    } else if (raw.length < 16) {
      byte[] result = new byte[16];
      System.arraycopy(raw, 0, result, 16 - raw.length, raw.length);
      return result;
    } else {
      // Remove extra leading byte(s) if present
      return Arrays.copyOfRange(raw, raw.length - 16, raw.length);
    }
  }

  public UUID parseOrThrow(String encoded) {
    if (Base62.isInvalidBase62Uuid(encoded)) {
      throw new IllegalArgumentException();
    }
    return decodeUuid(encoded);
  }
}
