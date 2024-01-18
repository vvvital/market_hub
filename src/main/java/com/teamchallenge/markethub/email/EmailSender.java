package com.teamchallenge.markethub.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@AllArgsConstructor
@Component
public class EmailSender {
    private final JavaMailSenderImpl javaMailSender;
    private final FreeMarkerConfigurer configurer;

    @Async
    public void sendAsyncMail(String email, String name, EmailSubjects subject) throws MessagingException, IOException, TemplateException {
        message(email, name, subject);
    }

    public void sendMail(String email, String name, EmailSubjects subject) throws MessagingException, IOException, TemplateException {
        message(email, name, subject);
    }

    private void message(String email, String name, EmailSubjects subject) throws MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();
        setMailProperties();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject(subject.text());
        switch (subject) {
            case WELCOME -> helper.setText(htmlBody(name, "welcome_authenticated_users.ftl"), true);
            case RESET_PASSWORD -> helper.setText(htmlBody(name, "password-change.ftl"), true);
        }

        this.javaMailSender.send(message);
    }

    private String htmlBody(String name, String template) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        Template freemarkerTemplate = configurer.getConfiguration()
                .getTemplate(template);
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
    }

    private void setMailProperties() {
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }
}
