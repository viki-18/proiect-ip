package com.yourorg.api.util;

import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;

@Component
public class CompressionUtil {

    /**
     * Compresses a string using GZIP and encodes it as Base64
     * 
     * @param input The string to compress
     * @return The Base64 encoded compressed string
     */
    public String compress(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            
            gzipOutputStream.write(input.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.finish();
            
            byte[] compressedBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(compressedBytes);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to compress data", e);
        }
    }
    
    /**
     * Decompresses a Base64 encoded GZIP compressed string
     * 
     * @param compressedBase64 The Base64 encoded compressed string
     * @return The original uncompressed string
     */
    public String decompress(String compressedBase64) {
        if (compressedBase64 == null || compressedBase64.isEmpty()) {
            return compressedBase64;
        }
        
        try {
            byte[] compressedBytes = Base64.getDecoder().decode(compressedBase64);
            
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedBytes);
                 GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                
                return outputStream.toString(StandardCharsets.UTF_8.name());
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to decompress data", e);
        }
    }
} 