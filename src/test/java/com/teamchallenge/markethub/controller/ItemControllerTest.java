package com.teamchallenge.markethub.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.teamchallenge.markethub.repository.ItemRepository;
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

    @Autowired
    private ItemRepository itemRepository;

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

    @Test
    public void shouldReturnAllItemsByCategoryId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedCount = itemRepository.findAllByCategoryId(100).size();
        int count = documentContext.read("$.length()");

        assertThat(count).isEqualTo(expectedCount);
    }

    @Test
    public void shouldReturnEmptyListItemsByIncorrectCategoryId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/1001", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int count = documentContext.read("$.length()");

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void shouldReturnAllItemsBySubCategoryId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/100/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedCount = itemRepository.findAllBySubCategoryId(100).size();
        int count = documentContext.read("$.length()");

        assertThat(count).isEqualTo(expectedCount);
    }

    @Test
    public void shouldReturnBadRequestIfUnknownCategoryId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/1000/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnItemById() {

    }
}
