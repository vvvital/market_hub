package com.teamchallenge.markethub.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@AllArgsConstructor
@Component
public class EmailSender {
    private final JavaMailSenderImpl javaMailSender;
    private final FreeMarkerConfigurer configurer;

    @Async
    public void asyncSendMail(String email, Map<String, Object> params, CustomTemplates template) throws MessagingException, IOException, TemplateException {
        MimeMessage message = configureMimeMessage(email, params, template);
        this.javaMailSender.send(message);
    }

    public void sendMail(String email, Map<String, Object> params, CustomTemplates template) throws MessagingException, IOException, TemplateException {
        MimeMessage message = configureMimeMessage(email, params, template);
        this.javaMailSender.send(message);
    }

    private MimeMessage configureMimeMessage(String email, Map<String, Object> params, CustomTemplates template) throws MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();
        setMailProperties();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject(template.getSubject());
        helper.setText(getStringTemplate(params, template.getTemplatePath()), true);
        return message;
    }

    private String getStringTemplate(Map<String, Object> model, String templatePath) throws TemplateException, IOException {
        Template freemarkerTemplate = configurer.getConfiguration()
                .getTemplate(templatePath);
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
    }

    private void setMailProperties() {
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }
}
