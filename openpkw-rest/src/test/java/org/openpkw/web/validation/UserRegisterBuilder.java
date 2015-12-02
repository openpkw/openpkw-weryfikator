package org.openpkw.web.validation;

import org.openpkw.model.entity.UserType;
import org.openpkw.web.dto.RegisterUserRequest;

public class UserRegisterBuilder {

    private RegisterUserRequest result = new RegisterUserRequest();

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

    public UserRegisterBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public UserRegisterBuilder withType(UserType userType) {
        result.setUserType(userType);
        return this;
    }

    public RegisterUserRequest build() {
        return result;
    }
}
