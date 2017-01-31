package org.openpkw.web.configuration;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private Integer port;

    @Value("${email.from}")
    private String from;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    @Bean
    public VelocityEngine velocityEngineFactoryBean() throws IOException {
        VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
        velocityEngineFactoryBean.setVelocityProperties(getVelocityProperties());
        return velocityEngineFactoryBean.createVelocityEngine();
    }

    @Bean
    public SimpleMailMessage templateMessage() {
        SimpleMailMessage template = new SimpleMailMessage();
        template.setFrom(from);
        return template;
    }

    private Properties getVelocityProperties() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        properties.setProperty("resourceLoaderPath", "/mail/");
        return properties;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.debug", "false");
        return properties;
    }
}
