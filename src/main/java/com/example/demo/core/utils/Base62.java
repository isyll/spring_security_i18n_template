package com.example.demo.core.utils;

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

  public static UUID decode(String base62) {

    byte[] bytes = decodeToBytes(base62);
    return fromBytes(bytes);
  }

  private static byte[] toBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
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

  public static String encode(byte[] data) {
    BigInteger bi = new BigInteger(1, data);
    StringBuilder sb = new StringBuilder();
    while (bi.compareTo(BigInteger.ZERO) > 0) {
      BigInteger[] divmod = bi.divideAndRemainder(BigInteger.valueOf(62));
      sb.append(BASE62.charAt(divmod[1].intValue()));
      bi = divmod[0];
    }
    return sb.reverse().toString();
  }

  private static byte[] decodeToBytes(String base62) {
    BigInteger bi = BigInteger.ZERO;
    for (char c : base62.toCharArray()) {
      bi = bi.multiply(BigInteger.valueOf(62)).add(BigInteger.valueOf(BASE62.indexOf(c)));
    }
    byte[] bytes = bi.toByteArray();
    if (bytes.length > 16) return Arrays.copyOfRange(bytes, bytes.length - 16, bytes.length);
    byte[] full = new byte[16];
    System.arraycopy(bytes, 0, full, 16 - bytes.length, bytes.length);
    return full;
  }
}
