package org.openpkw.weryfikator.rest.usersAndSessions;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;
import org.openpkw.weryfikator.rest.JAXRSTestBase;
import org.openpkw.weryfikator.rest.helper.MessageHelper;
import org.openpkw.weryfikator.rest.helper.ResponseDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.openpkw.weryfikator.rest.helper.UserHelper.*;

public class SessionsCreationTests extends JAXRSTestBase {

    @Test
    public void Should_return_session_token_if_user_exists_and_password_is_valid() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String password = UUID.randomUUID().toString();
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"" + password + "\"}";

        // Creating user
        ResponseDTO createUserResponse = callCreateUser(testContent);
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getHttpStatus(), is(equalTo(200)));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorCode"), is(equalTo("0")));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), is(equalTo("OK")));

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"" + password + "\"}";
        ResponseDTO session = callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + session.getResponseBody().get("errorMessage"), session.getHttpStatus(), is(equalTo(200)));
        Assert.assertThat("Session token was not returned: " + session.getResponseBody().get("data"), session.getResponseBody().get("data"), is(notNullValue()));

        // Deleting user after test
        ResponseDTO callDeleteUserResponse = callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + callDeleteUserResponse.getResponseBody().get("errorMessage"),
                                                                                callDeleteUserResponse.getHttpStatus(), is(equalTo(200)));

        // Checking that user has been deleted
        ResponseDTO callGetUserResponse = callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + callGetUserResponse.getResponseBody().get("errorMessage"),
                                                                                  callGetUserResponse.getHttpStatus(), is(equalTo(400)));
    }

    @Test
    public void Should_return_error_if_user_exists_but_password_is_not_valid() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String password = UUID.randomUUID().toString();
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"" + password+"\"}";

        // Creating user
        ResponseDTO createUserResponse = callCreateUser(testContent);
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), createUserResponse.getHttpStatus(), is(equalTo(200)));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorCode"), is(equalTo("0")));
        Assert.assertThat(createUserResponse.getResponseBody().get("errorMessage"), is(equalTo("OK")));

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"invalid-password\"}";
        ResponseDTO session = callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + session.getResponseBody().get("errorMessage"), session.getHttpStatus(), is(equalTo(400)));
        Assert.assertThat("Error code for INVALID_PASSWORD should be returned", session.getResponseBody().get("errorCode"), is(equalTo("301")));

        // Deleting user after test
        ResponseDTO callDeleteUserResponse = callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + callDeleteUserResponse.getResponseBody().get("errorMessage"),
                                                                                callDeleteUserResponse.getHttpStatus(), is(equalTo(200)));

        // Checking that user has been deleted
        ResponseDTO callGetUserResponse = callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + callGetUserResponse.getResponseBody().get("errorMessage"),
                                                                                  callGetUserResponse.getHttpStatus(), is(equalTo(400)));
    }    
    
    @Test
    public void Should_return_error_if_user_does_not_exist() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"random-password\"}";
        ResponseDTO session = callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + session.getResponseBody().get("errorMessage"), session.getHttpStatus(), is(equalTo(400)));
        Assert.assertThat("Error code for USER_NOT_FOUND should be returned", session.getResponseBody().get("errorCode"), is(equalTo("300")));
    }

    private ResponseDTO callCreateSession(String userCredentials) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.getHost() + "/sessions/");
        Response response = target.request().post(Entity.json(userCredentials), Response.class);
        return MessageHelper.getResponseDTO(response);
    }

}