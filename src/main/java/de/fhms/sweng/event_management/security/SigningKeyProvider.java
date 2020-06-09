package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.exceptions.NotAuthorizedException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class SigningKeyProvider {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    protected void loadSigningKeys() {
        try {
            //read privateKey
            InputStream inputStream = new ClassPathResource("privateKey").getInputStream();
            byte[] encodedPrivateKey = IOUtils.toByteArray(inputStream);

            //encode privateKey
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            this.privateKey = keyFactory.generatePrivate(privateKeySpec);

            //read public key
            inputStream = new ClassPathResource("publicKey").getInputStream();
            byte[] encodedPublicKey = IOUtils.toByteArray(inputStream);

            //encode publicKey
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            this.publicKey = keyFactory.generatePublic(publicKeySpec);
        }  catch (Exception e) {
            throw new NotAuthorizedException("Reading keys went wrong. Try again");
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}


