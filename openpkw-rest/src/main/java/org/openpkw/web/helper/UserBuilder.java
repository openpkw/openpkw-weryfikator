package org.openpkw.web.helper;

import org.openpkw.services.user.dto.UserDTO;

public class UserBuilder {

    private UserDTO result = new UserDTO();

    public UserBuilder withFirstName(String firstName) {
        result.setFirst_name(firstName);
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        result.setLast_name(lastName);
        return this;
    }

    public UserBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public UserBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public UserDTO build() {
        return result;
    }
}