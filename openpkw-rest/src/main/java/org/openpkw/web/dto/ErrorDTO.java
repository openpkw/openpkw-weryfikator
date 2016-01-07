package org.openpkw.web.dto;

import java.io.Serializable;

public class ErrorDTO implements Serializable {

    private int code;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
