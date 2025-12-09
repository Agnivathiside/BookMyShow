package com.bookMyShowClone.authService.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    // Access token = 15 minutes
    private final long ACCESS_TOKEN_EXPIRY = 15 * 60 * 1000;

    // Refresh token = 30 days
    private final long REFRESH_TOKEN_EXPIRY = 30L * 24 * 60 * 60 * 1000;

    @Value("${spring.application.name}")
    private String issuer;

    public JwtUtil(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    // ----------------------------
    // ACCESS TOKEN
    // ----------------------------
    public String generateAccessToken(String userId, String email, List<String> roles) throws Exception {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRY);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(userId)
                .claim("email", email)
                .claim("roles", roles)
                .issuer(issuer)
                .issueTime(now)
                .expirationTime(expiry)
                .build();

        return signJwt(claims);
    }

    // ----------------------------
    // REFRESH TOKEN
    // ----------------------------
    public String generateRefreshToken(String userId) throws Exception {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXPIRY);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(userId)
                .issuer(issuer)
                .issueTime(now)
                .expirationTime(expiry)
                .claim("type", "refresh")
                .build();

        return signJwt(claims);
    }

    // ----------------------------
    // Validate and Parse
    // ----------------------------
    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
            return signedJWT.verify(verifier) &&
                    signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) throws Exception {
        return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    }

    public List<String> extractRoles(String token) throws Exception {
        return (List<String>) SignedJWT.parse(token).getJWTClaimsSet().getClaim("roles");
    }

    public String extractEmail(String token) throws Exception {
        return (String) SignedJWT.parse(token).getJWTClaimsSet().getClaim("email");
    }

    // ----------------------------
    // Helper
    // ----------------------------
    private String signJwt(JWTClaimsSet claims) throws Exception {
        JWSSigner signer = new RSASSASigner(privateKey);

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claims);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
