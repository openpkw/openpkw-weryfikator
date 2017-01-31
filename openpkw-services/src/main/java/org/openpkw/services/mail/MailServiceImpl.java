package org.openpkw.services.mail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.inject.Inject;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Inject
    private MailSender mailSender;

    @Inject
    private VelocityEngine velocityEngine;

    @Inject
    private SimpleMailMessage templateMessage;

    @Override
    public void sendMail(String to, MailTemplate template, Map<String, Object> model) {
        sendMail(to, template.getSubject(), VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/WEB-INF/mail/" + template.getTemplate(), "UTF-8", model));
    }

    @Override
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

    }

}
