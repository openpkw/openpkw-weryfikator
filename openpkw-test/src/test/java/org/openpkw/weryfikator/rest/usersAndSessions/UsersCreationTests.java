package org.openpkw.weryfikator.rest.usersAndSessions;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.JAXRSTestBase;
import org.openpkw.weryfikator.rest.helper.ResponseDTO;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.openpkw.weryfikator.rest.helper.UserHelper.*;

public class UsersCreationTests extends JAXRSTestBase {

    public static final String DEFAULT_PASSWORD = "asfd";

    @Test
    public void create_read_delete_test() {
        String testEmail = generateEmail();

        // First time /authorize/register is called it should be OK
        ResponseDTO createUserResponse = createUser(testEmail, DEFAULT_PASSWORD);
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getHttpStatus(), is(equalTo(200)));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorCode"), is(equalTo("0")));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), is(equalTo("OK")));

        // Checking that user has been created
        ResponseDTO callGetUserResponse = callGetUser(testEmail);
        Assert.assertThat("Test user has not been created:" + callGetUserResponse.getResponseBody().get("errorMessage"), callGetUserResponse.getHttpStatus(), is(equalTo(200)));

        // Deleting user after test
        ResponseDTO callDeleteUserResponse = callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + callDeleteUserResponse.getResponseBody().get("errorMessage"),
                                                                                callDeleteUserResponse.getHttpStatus(), is(equalTo(200)));

        // Checking that user has been deleted
        callGetUserResponse = callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + callGetUserResponse.getResponseBody().get("errorMessage"),
                                                                                  callGetUserResponse.getHttpStatus(), is(equalTo(400)));
    }

    @Test
    public void create_with_existing_email_test() {
        String testEmail = generateEmail();

        // First time /authorize/register is called it should be OK
        ResponseDTO createUserResponse = createUser(testEmail, DEFAULT_PASSWORD);
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getHttpStatus(), is(equalTo(200)));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorCode"), is(equalTo("0")));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), is(equalTo("OK")));

        // Second time /authorize/register is called it should return an error
        createUserResponse = createUser(testEmail, DEFAULT_PASSWORD);
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getHttpStatus(), is(equalTo(400)));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getResponseBody().get("errorCode"), is(equalTo("200")));

        // Deleting user after test
        ResponseDTO callDeleteUserResponse = callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + callDeleteUserResponse.getResponseBody().get("errorMessage"),
                                                                                callDeleteUserResponse.getHttpStatus(), is(equalTo(200)));

        // Checking that user has been deleted
        ResponseDTO callGetUserResponse = callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + callGetUserResponse.getResponseBody().get("errorMessage"),
                                                                                  callGetUserResponse.getHttpStatus(), is(equalTo(400)));
    }

}