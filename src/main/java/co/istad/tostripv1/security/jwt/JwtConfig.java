package co.istad.tostripv1.security.jwt;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class JwtConfig {


    @Bean("accessTokenKeyPair")
    KeyPair accessTokenKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean("accessTokenRsaKey")
    public RSAKey accessTokenRsaKey(@Qualifier("accessTokenKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //decode
    @Bean("accessTokenJwtDecoder")
    JwtDecoder accessTokenJwtDecoder(@Qualifier("accessTokenRsaKey") RSAKey accessTokenRsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(accessTokenRsaKey.toRSAPublicKey())
                .build();
    }

    @Bean("accessTokenJWKSource")
    JWKSource<SecurityContext> accessTokenJWKSource(@Qualifier("accessTokenRsaKey") RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, context) -> jwkSelector.select(jwkSet));
    }

    @Bean
    JwtEncoder accessTokenJwtEncoder(@Qualifier("accessTokenJWKSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

//---------------------------------------------------------------------------------------------------
//------------------------------------| Refresh Token Key Pairs |------------------------------------
//---------------------------------------------------------------------------------------------------
    @Bean("refreshTokenKeyPair")
    KeyPair refreshTokenKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
    @Bean("refreshTokenRasKey")
    public RSAKey refreshTokenRasKey(@Qualifier("refreshTokenKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //decode
    @Bean("refreshTokenJwtDecoder")
    JwtDecoder refreshTokenJwtDecoder(@Qualifier("refreshTokenRasKey") RSAKey refreshTokenRasKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(refreshTokenRasKey.toRSAPublicKey())
                .build();
    }

    @Bean("refreshTokenJwkSource")
    JWKSource<SecurityContext> refreshTokenJwkSource(@Qualifier("refreshTokenRasKey") RSAKey refreshTokenRasKey) {
        JWKSet jwkSet = new JWKSet(refreshTokenRasKey);
        return ((jwkSelector, context) -> jwkSelector.select(jwkSet));
    }

    @Bean("refreshTokenJwtEncoder")
    JwtEncoder refreshTokenJwtEncoder(@Qualifier("refreshTokenJwkSource") JWKSource<SecurityContext> refreshTokenjwkSource) {
        return new NimbusJwtEncoder(refreshTokenjwkSource);
    }
}
