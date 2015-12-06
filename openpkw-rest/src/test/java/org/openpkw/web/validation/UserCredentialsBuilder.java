package org.openpkw.web.validation;

import org.openpkw.web.dto.UserCredentialsDTO;

public class UserCredentialsBuilder {

    private UserCredentialsDTO result = new UserCredentialsDTO();

    public UserCredentialsBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public UserCredentialsBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public UserCredentialsDTO build() {
        return result;
    }
}