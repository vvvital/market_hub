//package com.teamchallenge.markethub.controller;
//
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
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
//public class CategoryControllerTest {
//
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Test
//    public void shouldReturnAllCategory() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/categories", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//
//        int countCategory = documentContext.read("$.length()");
//        assertThat(countCategory).isEqualTo(5);
//    }
//
//    @Test
//    public void shouldReturnCategoryById() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/categories/100", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        String nameCategory = documentContext.read("$['name']");
//
//        assertThat(nameCategory).isEqualTo("Комп’ютерна техніка");
//    }
//
//    @Test
//    public void shouldReturnNotFoundCategoryByUnknownId() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/categories/1001", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        int code = documentContext.read("$['errors']['code']");
//        String message = documentContext.read("$['errors']['message']");
//
//        assertThat(code).isEqualTo(404);
//        assertThat(message).isEqualTo("category_not_found");
//    }
//
//
//    @Test
//    public void shouldReturnAllSubCategoriesByParentId() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/categories/100/sub-categories", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        int countSubCategory = documentContext.read("$.length()");
//
//        assertThat(countSubCategory).isEqualTo(3);
//
//    }
//
//    @Test
//    public void shouldReturnAllBrandsInSubCategory(){
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/categories/brandsInSubCategory/100", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        int countSubCategory = documentContext.read("$.length()");
//
//        assertThat(countSubCategory).isEqualTo(5);
//    }
//}
