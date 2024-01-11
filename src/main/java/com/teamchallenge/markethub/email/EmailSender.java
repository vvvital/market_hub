package com.teamchallenge.markethub.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public final class EmailSender {

    private final JavaMailSenderImpl javaMailSender;
    private final FreeMarkerConfigurer configurer;

    public EmailSender(JavaMailSenderImpl javaMailSender, FreeMarkerConfigurer configurer) {
        this.javaMailSender = javaMailSender;
        this.configurer = configurer;
    }

    public void sendEmail(String email, String firstname, String lastname) throws MessagingException, IOException, TemplateException {
        setMailProperties();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Welcome to MarketHub!");
        String htmlBody = htmlBody(firstname, lastname);
        helper.setText(htmlBody, true);

        this.javaMailSender.send(message);
    }

    private String htmlBody(String firstname, String lastname) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("firstname", firstname);
        model.put("lastname", lastname);

        Template freemarkerTemplate = configurer.getConfiguration()
                .getTemplate("welcome_authenticated_users.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
    }

    private void setMailProperties() {
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }
}
