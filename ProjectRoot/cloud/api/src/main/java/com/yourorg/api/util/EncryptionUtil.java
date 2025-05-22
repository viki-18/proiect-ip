package com.yourorg.api.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private final StandardPBEStringEncryptor encryptor;
    
    public EncryptionUtil(@Value("${app.encryption.password:defaultEncryptionPassword}") String encryptionPassword) {
        this.encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptionPassword);
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
    }
    
    /**
     * Encrypts a JSON string
     * 
     * @param json The JSON string to encrypt
     * @return Base64 encoded encrypted string
     */
    public String encrypt(String json) {
        String encrypted = encryptor.encrypt(json);
        return Base64.getEncoder().encodeToString(encrypted.getBytes());
    }
    
    /**
     * Decrypts a previously encrypted string
     * 
     * @param encryptedBase64 The encrypted Base64 string
     * @return The original JSON string
     */
    public String decrypt(String encryptedBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedBase64);
        String encrypted = new String(decodedBytes);
        return encryptor.decrypt(encrypted);
    }
} 