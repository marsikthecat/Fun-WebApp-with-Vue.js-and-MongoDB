package org.example.backend;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TokenManager {

  private static final String LETTERSUPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LETTERSLOWERCASE = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "1234567890";
  private static final SecureRandom random = new SecureRandom();
  private static final Set<String> tokens = Collections.synchronizedSet(new HashSet<>());

  private TokenManager() {}

  public static String setToken(int len) {
    String all = LETTERSUPPERCASE.concat(NUMBERS).concat(LETTERSLOWERCASE);
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < len; i++) {
      str.append(all.charAt(random.nextInt(all.length())));
    }
    String token = str.toString();
    tokens.add(token);
    return token;
  }

  public static String destroyToken(String token) {
    return tokens.remove(token) ? token : null;
  }

  public static boolean exists(String token) {
    return tokens.contains(token);
  }
}