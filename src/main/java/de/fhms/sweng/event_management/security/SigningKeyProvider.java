package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.exceptions.NotAuthorizedException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void loadSigningKeys() {
        try {
            //read privateKey
            LOGGER.trace("try to read privateKey");
            InputStream inputStream = new ClassPathResource("privateKey").getInputStream();
            byte[] encodedPrivateKey = IOUtils.toByteArray(inputStream);
            LOGGER.trace("success reading privateKey");

            //encode privateKey
            LOGGER.trace("try to encode privateKey");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            this.privateKey = keyFactory.generatePrivate(privateKeySpec);
            LOGGER.trace("success encoding privateKey");

            //read public key
            LOGGER.trace("try to read publicKey");
            inputStream = new ClassPathResource("publicKey").getInputStream();
            byte[] encodedPublicKey = IOUtils.toByteArray(inputStream);
            LOGGER.trace("success reading publicKey");

            //encode publicKey
            LOGGER.trace("try to encode publicKey");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            this.publicKey = keyFactory.generatePublic(publicKeySpec);
            LOGGER.trace("success encoding publicKey");

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
