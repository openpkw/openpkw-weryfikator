package org.openpkw.weryfikator.rest;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class JAXRSTestBase {

    protected Client createClient() {
        ClientConfig configuration = new ClientConfig();
        configuration.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        configuration.property(ClientProperties.READ_TIMEOUT, 5000);
        return ClientBuilder.newClient(configuration);
    }

}