package com.teamchallenge.markethub.email;

public enum EmailSubjects {

    WELCOME ("Welcome to MarketHub"),
    RESET_PASSWORD ("Reset password");

    private final String subject;

    EmailSubjects(String subject) {
        this.subject = subject;
    }

    public String text() {
        return subject;
    }
}
