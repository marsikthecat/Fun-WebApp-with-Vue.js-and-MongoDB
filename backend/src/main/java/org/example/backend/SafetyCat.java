package org.example.backend;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SafetyCat {

  private static final int IV_SIZE = 16;

  public static byte[] generateOtpKey(int length) {
    byte[] key = new byte[length];
    new SecureRandom().nextBytes(key);
    return key;
  }

  public static byte[] generateSalt() {
    return generateOtpKey(16);
  }

  public static byte[] encryptWithOtp(String content, byte[] key) {
    byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
    byte[] encryptedWithOtp = new byte[content.length()];
    for (int i = 0; i < content.length(); i++) {
      encryptedWithOtp[i] = (byte) (contentBytes[i] ^ key[i]);
    }
    return encryptedWithOtp;
  }

  public static SecretKey aesKeyFromPasswort(String password, byte[] salt) throws Exception {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    byte[] keyBytes = factory.generateSecret(spec).getEncoded();
    return new SecretKeySpec(keyBytes, "AES");
  }

  public static byte[] encryptOtpKeyWithAes(byte[] otpKey, SecretKey key) throws Exception {
    byte[] iv = new byte[IV_SIZE];
    new SecureRandom().nextBytes(iv);
    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
    byte[] encryptedData = cipher.doFinal(otpKey);
    byte[] result = new byte[iv.length + encryptedData.length];
    System.arraycopy(iv, 0, result, 0, iv.length);
    System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);
    return result;
  }

  public static byte[] decryptOtpKeyWithAes(byte[] encryptedData, SecretKey key) throws Exception {
    IvParameterSpec ivSpec = new IvParameterSpec(encryptedData, 0, IV_SIZE);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    return cipher.doFinal(encryptedData, IV_SIZE, encryptedData.length - IV_SIZE);
  }
}