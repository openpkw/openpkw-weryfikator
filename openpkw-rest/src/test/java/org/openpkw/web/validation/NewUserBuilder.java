package org.openpkw.web.validation;

import org.openpkw.web.dto.NewUserDTO;

public class NewUserBuilder {

    private NewUserDTO result = new NewUserDTO();

    public NewUserBuilder withFirstName(String firstName) {
        result.setFirst_name(firstName);
        return this;
    }

    public NewUserBuilder withLastName(String lastName) {
        result.setLast_name(lastName);
        return this;
    }

    public NewUserBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public NewUserBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public NewUserDTO build() {
        return result;
    }
}