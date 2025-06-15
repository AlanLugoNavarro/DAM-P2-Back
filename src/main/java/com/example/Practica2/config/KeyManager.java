package com.example.Practica2.config;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Base64;

public class KeyManager {

    public static void saveKey(String fileName) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(encodedKey.getBytes());
        }
    }

    public static SecretKey loadKey(String fileName) throws Exception {
        File file = new File(fileName);
        byte[] encodedKey;
        try (FileInputStream fis = new FileInputStream(file)) {
            encodedKey = fis.readAllBytes();
        }
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new javax.crypto.spec.SecretKeySpec(decodedKey, "AES");
    }
}