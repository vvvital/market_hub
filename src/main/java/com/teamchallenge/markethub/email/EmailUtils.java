package com.teamchallenge.markethub.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public final class EmailUtils {

    private final FreeMarkerConfigurer configurer;

    public EmailUtils(FreeMarkerConfigurer configurer) {
        this.configurer = configurer;
    }

    public String htmlBodyForTheGreetingEmail(String firstname, String lastname) throws IOException, TemplateException {
        Template freemarkerTemplate = configurer.getConfiguration()
                .getTemplate("welcome_authenticated_users.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model(firstname, lastname));
    }

    public String htmlBodyForPasswordChange(String firstname, String lastname) throws IOException, TemplateException {
        Template freemarkerTemplate = configurer.getConfiguration()
                .getTemplate("password-change.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model(firstname, lastname));
    }

    private Map<String, Object> model(String firstname, String lastname) {
        Map<String, Object> model = new HashMap<>();
        model.put("firstname", firstname);
        model.put("lastname", lastname);
        return model;
    }
}
