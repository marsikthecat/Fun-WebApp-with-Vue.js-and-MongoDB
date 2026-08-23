package org.example.backend;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManager {

  private static final String LETTERSUPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LETTERSLOWERCASE = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "1234567890";
  private static final SecureRandom random = new SecureRandom();
  private static final Map<String, Boolean> tokens = new ConcurrentHashMap<>();

  private TokenManager() {}

  public static String setToken(int len) {
    return setToken(len, false);
  }

  public static String setToken(int len, boolean isAdmin) {
    String all = LETTERSUPPERCASE.concat(NUMBERS).concat(LETTERSLOWERCASE);
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < len; i++) {
      str.append(all.charAt(random.nextInt(all.length())));
    }
    String token = str.toString();
    tokens.put(token, isAdmin);
    return token;
  }

  public static String destroyToken(String token) {
    if (tokens.remove(token) == null) {
      return null;
    }
    return token;
  }

  public static boolean exists(String token) {
    return tokens.containsKey(token);
  }

  public static boolean isAdmin(String token) {
    return Boolean.TRUE.equals(tokens.get(token));
  }
}