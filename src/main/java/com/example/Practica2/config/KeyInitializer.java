package com.example.Practica2.config;

import java.io.File;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class KeyInitializer {

    private final String keyPath = "src/main/resources/secretKey.txt";
    private SecretKey secretKey;

    @PostConstruct
    public void initKey() throws Exception {
        File file = new File(keyPath);
        if (file.exists()) {
            secretKey = KeyManager.loadKey(keyPath);
            System.out.println("Clave cargada automáticamente");
        } else {
            KeyManager.saveKey(keyPath);
            secretKey = KeyManager.loadKey(keyPath);
            System.out.println("Clave creada y cargada automáticamente");
        }
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
    
}