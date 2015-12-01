package org.openpkw.web.validation;

import org.openpkw.web.Autorize;

public class AuthorizeBuilder {

    private Autorize result = new Autorize();

    public AuthorizeBuilder withEmail(String email) {
        result.setEmail(email);
        return this;
    }

    public AuthorizeBuilder withPassword(String password) {
        result.setPassword(password);
        return this;
    }

    public Autorize build() {
        return result;
    }
}