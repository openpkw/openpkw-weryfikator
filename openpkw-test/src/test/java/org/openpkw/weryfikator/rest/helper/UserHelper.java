package org.openpkw.weryfikator.rest.helper;

import org.openpkw.weryfikator.rest.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Calendar;

/**
 * Utility class for user operation
 * @author Sebastian Pogorzelski
 */
public class UserHelper {


    public static ResponseDTO callCreateUser(String testContent) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/");
        Response response = target.request().post(Entity.json(testContent), Response.class);
        return MessageHelper.getResponseDTO(response);
    }

    public static  ResponseDTO callGetUser(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/" + email);
        Response response = target.request().get(Response.class);
        return MessageHelper.getResponseDTO(response);
    }

    public static  ResponseDTO callDeleteUser(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Configuration.getHost() + "/users/" + email);
        Response response = target.request().delete(Response.class);
        return MessageHelper.getResponseDTO(response);
    }

    public static ResponseDTO createUser(String email, String password) {
        String testContent = "{\"email\":\"" + email + "\",\"first_name\":\"first-name\",\"last_name\":\"last-name\",\"password\":\"" + password + "\"}";
        return callCreateUser(testContent);
    }

    public static String generateEmail() {
        return Long.toString(Calendar.getInstance().getTimeInMillis()) + "@test.com";
    }

}
