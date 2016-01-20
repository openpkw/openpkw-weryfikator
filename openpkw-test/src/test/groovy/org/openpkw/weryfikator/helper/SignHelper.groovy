package org.openpkw.weryfikator.helper

import java.security.KeyPairGenerator
import java.security.SecureRandom

class SignHelper {

    def static createPairKey() {
        def keyGen = KeyPairGenerator.getInstance("EC");
        def random = SecureRandom.getInstance("SHA1PRNG");

        keyGen.initialize(256, random);

        keyGen.generateKeyPair();
    }

    /*

    public static void main(String[] args) throws Exception {

        // Generate an ECDSA signature



      //Generate a key pair


    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

    keyGen.initialize(256, random);

    KeyPair pair = keyGen.generateKeyPair();
    PrivateKey priv = pair.getPrivate();
    PublicKey pub = pair.getPublic();


    // Create a Signature object and initialize it with the private key


    Signature dsa = Signature.getInstance("SHA1withECDSA");

    dsa.initSign(priv);

    String str = "This is string to sign";
    byte[] strByte = str.getBytes("UTF-8");
    dsa.update(strByte);


     //Now that all the data to be signed has been read in, generate a  signature for it


    byte[] realSig = dsa.sign();
    System.out.println("Signature: " + new BigInteger(1, realSig).toString(16));

}

     */

}