package org.openpkw.services.mail;

import java.util.Map;

/**
 * Created by Sebastian on 24.01.2017.
 */
public interface MailService {
    void sendMail(String to, MailTemplate template, Map<String, Object> model);

    void sendMail(String to, String subject, String text);
}
