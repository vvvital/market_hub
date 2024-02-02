package com.teamchallenge.markethub.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.teamchallenge.markethub.repository.ItemRepositoryTest;
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
    private ItemRepositoryTest itemRepositoryTest;

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
    public void shouldReturnItemsPage0Size1() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = 1;
        int currentSize = documentContext.read("$.length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsByCategory() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = itemRepositoryTest.findAllByCategoryId(100).size();
        int currentSize = documentContext.read("$.length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsBySubCategory() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = itemRepositoryTest.findAllBySubCategoryId(100).size();
        int currentSize = documentContext.read("$.length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsByCategoryIdSortedByCreateAtDesc() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?sort=createAt,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String expectedDate = "2024-01-26T14:12:00";
        System.out.println(response);
        String dateFirstItem = documentContext.read("$[0].['create_at']");

        assertThat(dateFirstItem).isEqualTo(expectedDate);
    }

    @Test
    public void shouldReturnAllItemsBySubCategoryIdSortedByCreateAtDesc() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?sort=createAt,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String expectedDate = "2024-01-26T14:12:00";
        System.out.println(response);
        String dateFirstItem = documentContext.read("$[0].['create_at']");

        assertThat(dateFirstItem).isEqualTo(expectedDate);
    }

    @Test
    public void shouldReturnAllItemsByCategoryIdSortedByPrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?sort=price,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Double expectedPrice = 29999.00;
        Double priceFirstItem = documentContext.read("$[0].['price']");

        assertThat(priceFirstItem).isEqualTo(expectedPrice);
    }

    @Test
    public void shouldReturnAllItemsBySubCategoryIdSortedByPrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?sort=price,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Double expectedPrice = 29999.00;
        Double priceFirstItem = documentContext.read("$[0].['price']");

        assertThat(priceFirstItem).isEqualTo(expectedPrice);
    }

    @Test
    public void shouldReturnEmptyListIfIdIsUnknown() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/101", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnItemById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/153", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn404IfItemNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/154", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
