package org.openpkw.services.sign;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignService.class);

    private static final String SIGNATURE_INSTANCE = "SHA256withECDSA";

    private static final String ALGORITHM = "ECDSA";

    private static final String SECURITY_PROVIDER = "BC";

    private static final String CURVE = "secp256k1";

    private static final String CHARACTER_ENCODING = "UTF-8";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public KeyPair generateKeys() {
        try {
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(CURVE);
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, SECURITY_PROVIDER);
            keyPairGenerator.initialize(ecSpec, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        }
        catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            LOGGER.error("Can't generate key pair", e);
        }
        return null;
    }

    public byte[] generateSignature(String data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_INSTANCE, SECURITY_PROVIDER);
            signature.initSign(privateKey);
            signature.update(data.getBytes(CHARACTER_ENCODING));
            return signature.sign();

        }
        catch (NoSuchAlgorithmException | NoSuchProviderException |
                SignatureException | InvalidKeyException | UnsupportedEncodingException e) {
            LOGGER.error("Can't sign data", e);
        }
        return null;
    }

    public boolean signatureVerification(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature signVerify = Signature.getInstance(SIGNATURE_INSTANCE, SECURITY_PROVIDER);
            signVerify.initVerify(publicKey);
            signVerify.update(data.getBytes(CHARACTER_ENCODING));
            return signVerify.verify(signature);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException |
                SignatureException | InvalidKeyException | UnsupportedEncodingException e) {
            LOGGER.error("Can't verify data", e);
        }
        return false;
    }

    public PublicKey getPublicKeyFromBase64(String publicKey) {
        try {
            byte[] bytePublicKey = Base64.getDecoder().decode(publicKey.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(bytePublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, SECURITY_PROVIDER);
            return keyFactory.generatePublic(X509publicKey);
        }
        catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException e) {
            LOGGER.error("Can't create public key from string", e);
        }
        return null;
    }

    public PrivateKey getPrivateKeyFromBase64(String privateKey) {
        try {
            byte[] bytePrivateKey = Base64.getDecoder().decode(privateKey.getBytes());
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, SECURITY_PROVIDER);
            return keyFactory.generatePrivate(encodedKeySpec);
        }
        catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException e) {
            LOGGER.error("Can't create private key from string", e);
        }
        return null;
    }




}
