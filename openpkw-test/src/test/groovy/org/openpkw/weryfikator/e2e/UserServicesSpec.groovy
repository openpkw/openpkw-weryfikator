package org.openpkw.weryfikator.e2e

import spock.lang.Specification

import static org.openpkw.weryfikator.invoker.UserServicesInvoker.*

class UserServicesSpec extends Specification {

    def static final DEFAULT_PASSWORD = "default_password";

    def "should create new user"() {

        given:

        def testEmail = generateEmail()

        when:

        def createUserResponse = createUser(testEmail, DEFAULT_PASSWORD)

        then:

        createUserResponse.status == 200
        createUserResponse.jsonObject.errorCode == "0"
        createUserResponse.jsonObject.errorMessage == "OK"

        and:
        def getUserResponse = getUser(testEmail)

        then:

        getUserResponse.status == 200
        getUserResponse.jsonObject.email == testEmail
        getUserResponse.jsonObject.errors == null


        cleanup:
        deleteUser(testEmail)

    }

    def "should not create user with the same email"() {
        given:

        def testEmail = generateEmail()
        createUser(testEmail, DEFAULT_PASSWORD)

        when:

        def createUserResponse = createUser(testEmail, DEFAULT_PASSWORD)

        then:

        createUserResponse.status == 400
        createUserResponse.jsonObject.errorMessage == "User with given e-mail address already exists"
        createUserResponse.jsonObject.errorCode == "200"

        cleanup:
        deleteUser(testEmail)
    }


    def "should login to application"() {
        given:
        def email = generateEmail()
        createUser(email, DEFAULT_PASSWORD)

        when:
        def loginResponse = login(email, DEFAULT_PASSWORD);
        String token = retriveToken(loginResponse);

        then:
        loginResponse.getStatus() == 200
        token != null

        cleanup:
        deleteUser(email);
    }


    def "should logout from application"() {
        given:
        def email = generateEmail()
        createUser(email, DEFAULT_PASSWORD)

        def loginResponse = login(email, DEFAULT_PASSWORD);
        String token = retriveToken(loginResponse);

        when:
        def logoutResponse = logout(token)

        then:
        logoutResponse.getStatus() == 200

        cleanup:
        deleteUser(email);
    }

    def "should get created user"() {
        given:

        def testEmail = generateEmail()
        createUser(testEmail, DEFAULT_PASSWORD)

        when:
        def userResponse = getUser(testEmail)

        then:
        userResponse.status == 200
        userResponse.jsonObject.email == testEmail
        userResponse.jsonObject.errors == null

        cleanup:
        deleteUser(testEmail)
    }

}