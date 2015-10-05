package org.openpkw.weryfikator.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test for health rest service check & security
 * @author Sebastian Pogorzelski
 */
public class When_sending_GET_request_to_healthCheck_url {


    @Test
    public void Should_return_the_simple_test_ok() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/openpkw/healthCheck/simpleTest");
        String response = target.request().get(String.class);

        assertThat(response, equalTo("HealthCheck Simple Test OK"));
    }

    @Test
    public void Should_return_the_data_test_ok() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/openpkw/healthCheck/dataTest");
        String response = target.request().get(String.class);

        assertThat(response, equalTo("HealthCheck Data Test OK"));
    }

    @Test
    public void Should_return_the_security_test_ok_after_authenticate() {

        Client client = ClientBuilder.newClient();
        String token = authenticate(client);

        String response = getSecurityTest(client, token);

        assertThat(response, equalTo("HealthCheck Security Test OK"));

    }


    private String getSecurityTest(Client client, String token) {
        WebTarget target = client.target("http://localhost:8080/openpkw/healthCheck/securityTest");
        return target.request()
                .header("x-auth-token", token)
                .get(String.class);
    }

    private String authenticate(Client client) {
        WebTarget target = client.target("http://localhost:8080/openpkw/api/authenticate");
        Form form = new Form();
        form.param("username", "admin@openpkw.pl");
        form.param("password", "admin");

        String response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form), String.class);
        return JsonPath.read(response, "token");
    }

}
