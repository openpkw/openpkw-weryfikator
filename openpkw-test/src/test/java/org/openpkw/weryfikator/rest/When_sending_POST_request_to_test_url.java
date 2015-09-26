package org.openpkw.weryfikator.rest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class When_sending_POST_request_to_test_url {

    @Test
    public void Should_return_the_same_content_that_wast_sent() {
        String testContent = "{\"test\":\"OpenPKW rules\"}";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/openpkw/test/echo");
        String response = target.request().post(Entity.json(testContent), String.class);

        assertThat(response, equalTo(testContent));
    }
}