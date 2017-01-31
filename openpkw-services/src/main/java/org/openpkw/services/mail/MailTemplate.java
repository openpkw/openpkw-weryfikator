package org.openpkw.services.mail;

public enum MailTemplate {

    PASSWORD_CHANGE("OpenPKW - reset hasła", "passwordChangeTemplate.vm");

    private final String template;
    private final String subject;

    MailTemplate(String subject, String template) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public String getSubject() {
        return subject;
    }
}
