package com.teamchallenge.markethub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


@SpringBootApplication
public class MarketHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketHubApplication.class, args);
    }
}
