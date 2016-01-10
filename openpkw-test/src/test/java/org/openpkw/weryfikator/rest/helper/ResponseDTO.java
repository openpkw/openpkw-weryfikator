package org.openpkw.weryfikator.rest.helper;

import java.util.Map;

/**
 * Response DTO
 * @author Sebastian Pogorzelski
 */
public class ResponseDTO {

    private int httpStatus;
    private Map<String, String> responseBody;

    public ResponseDTO(int httpStatus, Map<String, String> responseBody) {
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getResponseBody() {
        return responseBody;
    }
}
