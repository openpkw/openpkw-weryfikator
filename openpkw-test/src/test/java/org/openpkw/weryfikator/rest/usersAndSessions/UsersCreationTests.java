package org.openpkw.weryfikator.rest.usersAndSessions;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Calendar;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;
import org.openpkw.weryfikator.rest.JAXRSTestBase;

public class UsersCreationTests extends JAXRSTestBase {

    private int httpStatus;
    private Map<String, String> responseBody;

    @Test
    public void create_read_delete_test() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"asfd\"}";

        // First time /authorize/register is called it should be OK
        callCreateUser(testContent);
        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat(responseBody.get("errorCode"), is(equalTo("0")));
        Assert.assertThat(responseBody.get("errorMessage"), is(equalTo("OK")));

        // Checking that user has been created
        callGetUser(testEmail);
        Assert.assertThat("Test user has not been created:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));

        // Deleting user after test
        callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));

        // Checking that user has been deleted
        callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
    }

    @Test
    public void create_with_existing_email_test() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"asfd\"}";

        // First time /authorize/register is called it should be OK
        callCreateUser(testContent);
        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat(responseBody.get("errorCode"), is(equalTo("0")));
        Assert.assertThat(responseBody.get("errorMessage"), is(equalTo("OK")));

        // Second time /authorize/register is called it should return an error
        callCreateUser(testContent);
        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat(responseBody.get("errorMessage"), responseBody.get("errorCode"), is(equalTo("200")));

        // Deleting user after test
        callDeleteUser(testEmail);
        Assert.assertThat("Failed to delete test user during test teardown: " + responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));

        // Checking that user has been deleted
        callGetUser(testEmail);
        Assert.assertThat("Test user has not been deleted after test teardown:" + responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
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