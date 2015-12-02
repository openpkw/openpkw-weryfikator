package org.openpkw.web.validation;

/**
 * Katalog odpowiedzi web serwisu
 */
public enum RestClientErrorMessage {
    
    OK (0, "OK"),
    
    USER_EMAIL_IS_MANDATORY (100, "User e-mail is mandatory"),
    
    USER_FIRST_NAME_IS_MANDATORY (101, "User first name is mandatory"),
    
    USER_LAST_NAME_IS_MANDATORY (102, "User last name is mandatory"),
    
    USER_TYPE_IS_MANDATORY (103, "User type is mandatory"),
    
    USER_PASSWORD_IS_MANDATORY (104, "User password is mandatory"),
    
    USER_ALREADY_EXISTS (200, "User with given e-mail address already exists"),
    
    USER_NOT_FOUND (300, "User with given e-mail address does not exist");    
    
    
    int errorCode;
    String errorMessage;
    
    RestClientErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

}