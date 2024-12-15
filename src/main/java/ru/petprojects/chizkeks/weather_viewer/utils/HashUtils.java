package ru.petprojects.chizkeks.weather_viewer.utils;

import lombok.experimental.UtilityClass;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@UtilityClass
public class HashUtils {

    public String encodePBKDF2(String msg) {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(msg.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedMsg = factory.generateSecret(spec).getEncoded();
            return new String(hashedMsg, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problems with hashing algorithm", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Problems with hashing key", e);
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }



}
