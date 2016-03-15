package org.openpkw.weryfikator.helper

import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECParameterSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.PrivateKey

import java.security.SecureRandom
import java.security.Security
import java.security.Signature
import java.security.SignatureException


class SignHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignHelper.class);

    private static final String SIGNATURE_INSTANCE = "SHA256withECDSA";

    private static final String ALGORITHM = "ECDSA";

    private static final String SECURITY_PROVIDER = "BC";

    private static final String CURVE = "secp256k1";

    private static final String CHARACTER_ENCODING = "UTF-8";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static KeyPair createPairKey() {
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

    public static byte[] generateSignature(String data, PrivateKey privateKey) {
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

}