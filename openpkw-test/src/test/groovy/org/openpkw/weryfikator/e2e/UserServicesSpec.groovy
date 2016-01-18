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
        //TODO add status check

        and:
        def getUserResponse = getUser(testEmail)

        then:

        getUserResponse.status == 200
        //TODO add status check


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
        //TODO add status check


        cleanup:
        deleteUser(testEmail)
    }

    //TODO getUser test
    //TODO delete users test

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

}