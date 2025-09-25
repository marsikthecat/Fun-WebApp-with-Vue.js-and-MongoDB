package org.example.backend;

import java.util.HashMap;
import java.util.Random;

public class TokenManager {

  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String NUMBERS = "1234567890";
  private static final Random random = new Random();
  private static final HashMap<Integer, String> tokenSet = new HashMap<>();

  private TokenManager() {}

  public static String createToken(int len) {
    String all = LETTERS.concat(NUMBERS);
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < len; i++) {
      str.append(all.charAt(random.nextInt(all.length())));
    }
    return str.toString();
  }

  public static void addToken(String string, int userId) {
    tokenSet.put(userId, string);
  }

  public static String removeToken(int userId) {
    return tokenSet.remove(userId);
  }

  public static boolean exists(int userId) {
    return tokenSet.get(userId) != null;
  }
}