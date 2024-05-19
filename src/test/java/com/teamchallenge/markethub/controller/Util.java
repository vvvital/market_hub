//package com.teamchallenge.markethub.controller;
//
//import org.jboss.jandex.Main;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Base64;
//import java.util.Optional;
//
//public class Util {
//
//    public static String encodeToBase64(String filename) {
//        ClassLoader classLoader = Util.class.getClassLoader();
//
//        try(InputStream input = classLoader.getResourceAsStream(filename);) {
//            assert input != null;
//            System.out.println("SUCCESSFUL");
//            return "data:image/png;base64," + Base64.getEncoder().encodeToString(input.readAllBytes());
//        } catch (IOException e) {
//            System.out.println("FAILED");
//            return "";
//        }
//    }
//}
