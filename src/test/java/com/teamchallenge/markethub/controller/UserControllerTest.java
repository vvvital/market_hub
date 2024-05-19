//package com.teamchallenge.markethub.controller;
//
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
//import com.teamchallenge.markethub.model.User;
//import com.teamchallenge.markethub.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserControllerTest {
//
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    public void shouldReturnUserById() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/users/1", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        User user=userService.findById(1);
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//
//        String firstName = documentContext.read("$.firstname");
//        assertThat(firstName).isEqualTo(user.getFirstname());
//    }
//
//    @Test
//    public void shouldUpdateUserById() {
//
//    }
//}
