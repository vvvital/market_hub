package com.teamchallenge.markethub.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnTopSellerItem() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/top-seller", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        int countItem = documentContext.read("$.length()");
        assertThat(countItem).isEqualTo(4);
    }

    @Test
    public void shouldReturnSharesItem() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/shares", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        int countItem = documentContext.read("$.length()");
        assertThat(countItem).isEqualTo(4);
    }

}
