package org.openpkw.web.validation;

import org.openpkw.web.dto.AuthorizeUserRequest;

public class AuthorizeBuilder {

    private AuthorizeUserRequest result = new AuthorizeUserRequest();

    public AuthorizeBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public AuthorizeBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public AuthorizeUserRequest build() {
        return result;
    }
}