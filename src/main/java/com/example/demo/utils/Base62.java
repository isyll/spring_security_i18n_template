package com.example.demo.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class Base62 {
  private static final String BASE62 =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public static String encode(UUID uuid) {
    return encode(toBytes(uuid));
  }

  public static String encode(byte[] data) {
    BigInteger bi = new BigInteger(1, data); // Unsigned
    StringBuilder sb = new StringBuilder();
    while (bi.compareTo(BigInteger.ZERO) > 0) {
      BigInteger[] divMod = bi.divideAndRemainder(BigInteger.valueOf(62));
      sb.append(BASE62.charAt(divMod[1].intValue()));
      bi = divMod[0];
    }
    return sb.reverse().toString();
  }

  public static UUID decode(String base62) {
    byte[] bytes = decodeToBytes(base62);
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

  private static byte[] decodeToBytes(String base62) {
    BigInteger bi = BigInteger.ZERO;
    for (char c : base62.toCharArray()) {
      int index = BASE62.indexOf(c);
      if (index == -1) throw new IllegalArgumentException("Invalid character in base62: " + c);
      bi = bi.multiply(BigInteger.valueOf(62)).add(BigInteger.valueOf(index));
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
}
