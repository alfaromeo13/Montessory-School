package com.example.dedis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Description("Java Email sender integration configuration")
public class EmailConfig {

    @Value("${application.mail.host}")
    private String host;

    @Value("${application.mail.port}")
    private int port;

    @Value("${application.mail.username}")
    private String username;

    @Value("${application.mail.password}")
    private String password;

    @Value("${application.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${application.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);

        return mailSender;
    }
}