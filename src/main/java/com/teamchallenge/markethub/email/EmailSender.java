package com.teamchallenge.markethub.email;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public final class EmailSender {

    //todo: explore a pattern strategy
    private final JavaMailSenderImpl javaMailSender;
    private final EmailUtils emailUtils;
    private String email;
    private String firstname;
    private String lastname;
    private final MimeMessage message;
    private final MimeMessageHelper helper;

    public EmailSender(JavaMailSenderImpl javaMailSender, EmailUtils emailUtils) throws MessagingException {
        this.javaMailSender = javaMailSender;
        this.emailUtils = emailUtils;
        setMailProperties();
        this.message = javaMailSender.createMimeMessage();
        this.helper = new MimeMessageHelper(message, true);
    }

    public void sendGreetingEmail() throws MessagingException, IOException, TemplateException {
        helper.setTo(this.email);
        helper.setSubject(EmailSubjects.WELCOME.text());
        helper.setText(emailUtils.htmlBodyForTheGreetingEmail(firstname, lastname), true);

        this.javaMailSender.send(message);
    }

    public void sendEmailForPasswordChange() throws MessagingException, IOException, TemplateException {
        helper.setTo(this.email);
        helper.setSubject(EmailSubjects.RESET_PASSWORD.text());
        helper.setText(emailUtils.htmlBodyForPasswordChange(firstname, lastname), true);

        this.javaMailSender.send(message);
    }

    public void setParams(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    private void setMailProperties() {
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }
}
