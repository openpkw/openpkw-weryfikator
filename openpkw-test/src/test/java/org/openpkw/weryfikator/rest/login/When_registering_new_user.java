package org.openpkw.weryfikator.rest.login;

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
import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;

public class When_registering_new_user {

    private int httpStatus;
    private Map<String, String> responseBody;

    @Test
    public void should_return_error_100_if_user_email_address_is_not_privided() {
        String testContent = "{\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"userType\":\"ADMINISTRATOR\"}";
        callAuthorizeRegister(testContent);

        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat(responseBody.get("errorMessage"), responseBody.get("errorCode"), is(equalTo("100")));
    }

    @Test
    public void should_return_error_101_if_user_first_name_is_not_privided() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String testContent = "{\"email\":\"" + testEmail + "\",\"last_name\":\"last-name\",\"userType\":\"ADMINISTRATOR\"}";

        callAuthorizeRegister(testContent);

        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat(responseBody.get("errorMessage"), responseBody.get("errorCode"), is(equalTo("101")));
    }

    @Test
    public void should_return_error_102_if_user_last_name_is_not_privided() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
        String testContent = "{\"first_name\":\"first-name\",\"email\":\"" + testEmail + "\",\"userType\":\"ADMINISTRATOR\"}";

        callAuthorizeRegister(testContent);

        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat(responseBody.get("errorMessage"), responseBody.get("errorCode"), is(equalTo("102")));
    }

    @Test
    public void should_return_error_200_if_user_with_given_email_address_already_exists() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";

        String testContent = "{\"email\":\"" + testEmail + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"userType\":\"ADMINISTRATOR\"}";

        // First time /authorize/register is called it should be OK
        callAuthorizeRegister(testContent);

        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(200)));
        Assert.assertThat(responseBody.get("errorCode"), is(equalTo("0")));
        Assert.assertThat(responseBody.get("errorMessage"), is(equalTo("OK")));

        // Second time /authorize/register is called it should return an error
        callAuthorizeRegister(testContent);

        Assert.assertThat(responseBody.get("errorMessage"), httpStatus, is(equalTo(400)));
        Assert.assertThat(responseBody.get("errorMessage"), responseBody.get("errorCode"), is(equalTo("200")));
    }

    private void callAuthorizeRegister(String testContent) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/authorize/register");
        Response response = target.request().post(Entity.json(testContent), Response.class);
        httpStatus = response.getStatus();
        responseBody = getMessageBody(response);
    }

    private Map<String, String> getMessageBody(Response response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.readEntity(String.class), Map.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize message body: " + ex.getMessage(), ex);
        }
    }
}