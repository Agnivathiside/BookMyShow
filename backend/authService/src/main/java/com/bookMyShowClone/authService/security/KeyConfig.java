package com.bookMyShowClone.authService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;

@Configuration
public class KeyConfig {

    @Bean
    public PrivateKey privateKey() throws Exception {
        String key = loadKey("keys/private_key.pem");
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    @Bean
    public PublicKey publicKey() throws Exception {
        String key = loadKey("keys/public_key.pem");
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private String loadKey(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }
}
