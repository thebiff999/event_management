package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    private String username;
    private String role;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String token;

    @InjectMocks
    private JwtTokenProvider provider;

    @Mock
    private SigningKeyProvider keys;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp () throws Throwable {
        username="tom@fhms.de";
        role="EUSER";
        provider = new JwtTokenProvider();

        token="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0b21AZmhtcy5kZSIsImF1dGgiOiJFVVNFUiIsImlhdCI6MTU5MjY4MDE2OSwiZXhwIjoxNTkyNzg4MTY5fQ.N4akOphkOSezmyQ_I8jSTWe3csuAlI73K8OWevD3ZBrHcOA8fCUZQK1iZQF9Ktt_yHa8JXZXR9PrG6uqzufv9fUcNwfdsJsKYtdmVX4tj2-XVDH5DKD08MvNMNeGo9VUeDLWGwAwdVkQoOOvGCJHZ-CHiz3g-XaANNVd3johJInbJ-Omchr0dtzmCsUs4B-j5JUR5QihDnrP1lqFhulsnT_sX1_13KBT-w0m2E0U3nwVHx_vbsDERH0qnfLym1xoWAjEFpasTbJf-JhNMd2pR9X_MYkCPg80abqe1fr5qONs-yDk9d_iZIOl80r_HKcWA5L2O3a0wnatqpf5UyTUUQ";

        ReflectionTestUtils.setField(provider, "validityTime", 360000);
        ReflectionTestUtils.setField(provider, "keys", keys);
        ReflectionTestUtils.setField(provider, "userDetailsService", userDetailsService);

        InputStream inputStream = new ClassPathResource("privateKey").getInputStream();
        byte[] encodedPrivateKey = IOUtils.toByteArray(inputStream);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);

        inputStream = new ClassPathResource("publicKey").getInputStream();
        byte[] encodedPublicKey = IOUtils.toByteArray(inputStream);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);

    }

    @Test
    void createJwtTest() {
        given(keys.getPrivateKey()).willReturn(privateKey);
        assertNotNull(provider.createJwt(username,role));
    }

    @Test
    void isValidJwt() {
        given(keys.getPublicKey()).willReturn(publicKey);
        assertTrue(provider.isValidJWT(token));
    }

    @Test
    void isNotValidJwt() {
        given(keys.getPublicKey()).willReturn(publicKey);
        assertThrows(ResourceNotFoundException.class, () -> {
            provider.isValidJWT("12345");
        });
    }

}
