package org.openpkw.weryfikator.rest.helper;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Utility class for parse message
 * @author Sebastian Pogorzelski
 */
public class MessageHelper {

    public static Map<String, String> getMessageBody(Response response) {
        String json = null;
        try {
            json = response.readEntity(String.class);
            return MessageHelper.jsonToMap(json);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize message body: " + ex.getMessage() + "\nMessage body:\n" + json, ex);
        }
    }

    public static Map<String, String> jsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert String to json: " + ex.getMessage() + " Input: " + json, ex);
        }
    }
}
