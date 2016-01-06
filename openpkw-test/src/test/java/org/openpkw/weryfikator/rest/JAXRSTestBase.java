package org.openpkw.weryfikator.rest;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

public class JAXRSTestBase {

    protected Client createClient() {
        ClientConfig configuration = new ClientConfig();
        configuration.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        configuration.property(ClientProperties.READ_TIMEOUT, 5000);
        return ClientBuilder.newClient(configuration);
    }

    protected Map<String, String> getMessageBody(Response response) {
        String json = null;
        try {
            json = response.readEntity(String.class);
            return jsonToMap(json);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize message body: " + ex.getMessage() + "\nMessage body:\n" + json, ex);
        }
    }

    protected Map<String, String> jsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert String to json: " + ex.getMessage() + " Input: " + json, ex);
        }
    }
}