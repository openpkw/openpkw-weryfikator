package org.openpkw.weryfikator.rest.usersAndSessions;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;
import org.openpkw.weryfikator.rest.JAXRSTestBase;

public class SessionsCreationTests extends JAXRSTestBase {

    private int httpStatus;
    private Map<String, String> responseBody;

    @Test
    public void Should_return_session_token_if_user_exists_and_password_is_valid() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String password = UUID.randomUUID().toString();
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"" + password + "\"}";

        // Creating user
        callCreateUser(testContent);
        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat(responseBody.get("errorCode"), is(equalTo("0")));
        Assert.assertThat(responseBody.get("errorMessage"), is(equalTo("OK")));

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"" + password + "\"}";
        callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat("Session token was not returned: " + responseBody.get("data"), responseBody.get("data"), is(notNullValue()));

        // Deleting user after test
        callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));

        // Checking that user has been deleted
        callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
    }

    @Test
    public void Should_return_error_if_user_exists_but_password_is_not_valid() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String password = UUID.randomUUID().toString();
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"" + password+"\"}";

        // Creating user
        callCreateUser(testContent);
        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat(responseBody.get("errorCode"), is(equalTo("0")));
        Assert.assertThat(responseBody.get("errorMessage"), is(equalTo("OK")));

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"invalid-password\"}";
        callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat("Error code for INVALID_PASSWORD should be returned", responseBody.get("errorCode"), is(equalTo("301")));

        // Deleting user after test
        callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));

        // Checking that user has been deleted
        callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
    }    
    
    @Test
    public void Should_return_error_if_user_does_not_exist() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";

        // Trygint to log in
        String userCredentials = "{\"email\":\"" + testEmail + "\",\"password\":\"random-password\"}";
        callCreateSession(userCredentials);
        Assert.assertThat("Failed to log in using valid credentials:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat("Error code for USER_NOT_FOUND should be returned", responseBody.get("errorCode"), is(equalTo("300")));
    }   
    
    private void callCreateSession(String userCredentials) {
        Client client = createClient();
        WebTarget target = client.target(Configuration.getHost() + "/sessions/");
        Response response = target.request().post(Entity.json(userCredentials), Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callCreateUser(String testContent) {
        Client client = createClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/");
        Response response = target.request().post(Entity.json(testContent), Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callGetUser(String email) {
        Client client = createClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/" + email);
        Response response = target.request().get(Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callDeleteUser(String email) {
        Client client = createClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/" + email);
        Response response = target.request().delete(Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }
}