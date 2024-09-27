package io.github.javialc.java.labs.authorizeserver.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String secretKey = "secret";

    public String createToken(String email) {
        log.info("Creating token for email: {}", email);
        return JWT.create()
            .withSubject(email)
            .withIssuer("auth0")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 360000)) // 1 hour
            .sign(getAlgorithm());

    }


    public boolean validateToken(String token) {
        log.info("Validating token: {}", token);
        DecodedJWT decodedJWT;
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm())
                // specify any specific claim validations
                .withIssuer("auth0")
                // reusable verifier instance
                .build();

            decodedJWT = verifier.verify(token);
            log.info("Token is valid: {}",decodedJWT);
            return true;
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            log.warn("Token is invalid: {}",exception.getMessage());
            return false;
        }
    }

    public DecodedJWT validateTokenAndDecode(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm())
            // specify any specific claim validations
            .withIssuer("auth0")
            // reusable verifier instance
            .build();
        return verifier.verify(token);
    }


    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secretKey);
    }

}
