package org.openpkw.web.validation;

import org.openpkw.web.UserRegister;

public class UserRegisterBuilder {

    private UserRegister result = new UserRegister();

    public UserRegisterBuilder withFirstName(String firstName) {
        result.setFirst_name(firstName);
        return this;
    }

    public UserRegisterBuilder withLastName(String lastName) {
        result.setLast_name(lastName);
        return this;
    }

    public UserRegisterBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public UserRegister build() {
        return result;
    }
}
