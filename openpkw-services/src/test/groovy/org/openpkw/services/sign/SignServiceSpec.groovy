package org.openpkw.services.sign

import spock.lang.Specification

class SignServiceSpec extends Specification {

    public static final String TEST_DATA = "Data"

    SignService signService = new SignService()

    def "should create pair of keys"() {

        when:
        def keys = signService.generateKeys()

        then:
        keys != null
    }

    def "should get public key from string"() {
        given:
        def publicKeyString = Base64.getEncoder().encodeToString(signService.generateKeys().getPublic().getEncoded())

        when:
        def publicKey = signService.getPublicKeyFromBase64(publicKeyString)

        then:
        publicKey != null
    }

    def "should get private key from string"() {
        given:
        def privateKeyString = Base64.getEncoder().encodeToString(signService.generateKeys().getPrivate().getEncoded())

        when:
        def privateKey = signService.getPrivateKeyFromBase64(privateKeyString)

        then:
        privateKey != null

        println(Base64.getEncoder().encodeToString(signService.generateKeys().getPrivate().getEncoded()))
        println(Base64.getEncoder().encodeToString(signService.generateKeys().getPublic().getEncoded()))
    }

    def "should create signature for data"() {

        given:
        def keys = signService.generateKeys()

        when:
        def signature = signService.generateSignature(TEST_DATA, keys.getPrivate())

        then:
        signature != null
    }

    def "should verify signature for data"() {

        given:
        def keys = signService.generateKeys()
        def signature = signService.generateSignature(TEST_DATA, keys.getPrivate())

        when:
        def verification = signService.signatureVerification(keys.getPublic(), TEST_DATA, signature)

        then:
        verification
    }


}