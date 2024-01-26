package com.teamchallenge.markethub.email;

import lombok.Getter;

public enum CustomTemplates {
    WELCOME_AUTHENTICATED_USERS_TEMPLATE("welcome_authenticated_users.ftl", "Welcome to MarketHub"),
    PASSWORD_CHANGE_TEMPLATE("password-change.ftl", "Reset Password");

    private final String path;
    @Getter
    private final String subject;

    CustomTemplates(String path, String subject) {
        this.path = path;
        this.subject = subject;
    }

    public String getTemplatePath() {
        return this.path;
    }

}
