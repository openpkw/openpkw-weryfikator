package org.openpkw.weryfikator.rest.basicTests;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;

public class When_sending_GET_request_to_root_url {

    @Test
    public void should_return_Hello_World_HTML_page() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.getHost());
        String response = target.request().get(String.class);

        assertThat(response, containsString("Hello World"));
    }
}