package org.openpkw.weryfikator.rest.login;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.*;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;

public class SessionsCreationTests {

    private int httpStatus;
    private Map<String, String> responseBody;

    @Test
    public void Should_return_session_token_if_users_exists_and_password_is_valid() {
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

    private void callCreateSession(String userCredentials) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/sessions/");
        Response response = target.request().post(Entity.json(userCredentials), Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callCreateUser(String testContent) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/users/");
        Response response = target.request().post(Entity.json(testContent), Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callGetUser(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/users/" + email);
        Response response = target.request().get(Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private void callDeleteUser(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/users/" + email);
        Response response = target.request().delete(Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private Map<String, String> getMessageBody(Response response) {
        String json = null;
        try {
            json = response.readEntity(String.class);
            return jsonToMap(json);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize message body: " + ex.getMessage() + "\nMessage body:\n" + json, ex);
        }
    }

    private Map<String, String> jsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert String to json: " + ex.getMessage() + " Input: " + json, ex);
        }
    }
}