package com.teamchallenge.markethub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MarketHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketHubApplication.class, args);
        System.out.println("Welcome to the MarketHub");
    }
}