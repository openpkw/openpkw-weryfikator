package org.openpkw.weryfikator.rest.login;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Calendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;

public class When_registering_new_user {

    @Test
    public void should_return_error_if_email_address_is_not_privided() {
        String testContent = "{}";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/authorize/register");
        Response response = target.request().post(Entity.json(testContent), Response.class);
        int status = response.getStatus();
        String errorMessage = response.readEntity(String.class);

        Assert.assertThat(status, is(equalTo(400)));
        Assert.assertThat(errorMessage, is(equalTo("E-mail is mandatory")));
    }

    @Test
    public void should_return_error_if_user_with_given_email_address_already_exists() {
        String testEmail = Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";

        String testContent = "{\"email\":\"" + testEmail + "\"}";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.LOCALHOST + "/authorize/register");

        // First time /authorize/register is called it should be OK
        Response response = target.request().post(Entity.json(testContent), Response.class);
        int status = response.getStatus();
        String errorMessage = response.readEntity(String.class);

        Assert.assertThat(status, is(equalTo(200)));
        Assert.assertThat(errorMessage, is(equalTo("OK")));

        // Second time /authorize/register is called it should return an error
        response = target.request().post(Entity.json(testContent), Response.class);
        status = response.getStatus();
        errorMessage = response.readEntity(String.class);

        Assert.assertThat(status, is(equalTo(400)));
        Assert.assertThat(errorMessage, is(equalTo("User with given email address already exists")));
    }
}