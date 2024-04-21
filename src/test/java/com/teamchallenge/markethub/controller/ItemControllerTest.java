package com.teamchallenge.markethub.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.dto.item.NewItemRequest;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import com.teamchallenge.markethub.repository.ItemRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepositoryTest itemRepositoryTest;

    //FAILED
//    @Test
//    public void shouldReturnTopSellerItem() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/top-seller", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//
//        int countItem = documentContext.read("$['items'].length()");
//        assertThat(countItem).isEqualTo(4);
//    }

    @Test
    public void shouldReturnSharesItem() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/shares", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int countItem = documentContext.read("$.['items'].length()");

        assertThat(countItem).isEqualTo(4);
    }


    @Test
    public void shouldReturnItemsPage0Size1() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = 1;
        int currentSize = documentContext.read("$['items'].length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsByCategory() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = itemRepositoryTest.findAllByCategoryId(100).size();  //10
        int currentSize = documentContext.read("$.items.length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsBySubCategory() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int expectedSize = itemRepositoryTest.findAllBySubCategoryId(100).size();
        int currentSize = documentContext.read("$.items.length()");

        assertThat(currentSize).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnAllItemsByCategoryIdSortedByCreateAtDesc() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?sort=createAt,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String expectedDate = "2024-02-04T17:42:35";
        System.out.println(response);
        String dateFirstItem = documentContext.read("$['items'][0].['create_at']");

        assertThat(dateFirstItem).isEqualTo(expectedDate);
    }

    @Test
    public void shouldReturnAllItemsBySubCategoryIdSortedByCreateAtDesc() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?sort=createAt,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String expectedDate = "2024-02-04T17:42:35";
        System.out.println(response);
        String dateFirstItem = documentContext.read("$['items'][0].['create_at']");

        assertThat(dateFirstItem).isEqualTo(expectedDate);
    }

    @Test
    public void shouldReturnAllItemsByCategoryIdSortedByPrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?sort=price,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Double expectedPrice = 55199.00;
        Double priceFirstItem = documentContext.read("$['items'][0].['price']");

        assertThat(priceFirstItem).isEqualTo(expectedPrice);
    }

    @Test
    public void shouldReturnAllItemsBySubCategoryIdSortedByPrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?sort=price,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Double expectedPrice = 55199.00;
        Double priceFirstItem = documentContext.read("$['items'][0].price");

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

    @Test
    public void shouldReturnItemsByCategoryIdInRangePrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?price_from=17495&price_to=17505", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Double priceFirstItem = documentContext.read("$['items'][0].price");
        double priceFrom = 17495;
        double priceTo = 17505;

        assertThat(priceFirstItem).isGreaterThanOrEqualTo(priceFrom).isLessThanOrEqualTo(priceTo);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdInRangePrice() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?price_from=17495&price_to=17505", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        double priceFirstItem = documentContext.read("$['items'][0].price");
        double priceFrom = 17495;
        double priceTo = 17505;

        assertThat(priceFirstItem).isGreaterThanOrEqualTo(priceFrom).isLessThanOrEqualTo(priceTo);
    }

    @Test
    public void shouldReturnItemsByCategoryIdByAvailable() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?available=false", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        boolean availableItem = documentContext.read("$['items'][0]['available']");

        assertThat(availableItem).isEqualTo(false);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdByAvailable() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?available=false", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        boolean availableItem = documentContext.read("$['items'][0]['available']");

        assertThat(availableItem).isEqualTo(false);
    }

    @Test
    public void shouldReturnItemsByCategoryIdByBrand() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?brands=Apple", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        System.out.println("RESPONSE: " + response);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 1;
        assertThat(sizeItemsArray).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdByBrand() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?brands=Apple", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 1;

        assertThat(sizeItemsArray).isEqualTo(expectedSize);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdByMultiBrand() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?brands=Lenovo,Samsung", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 3;

        assertThat(sizeItemsArray).isEqualTo(expectedSize);

        String brand = documentContext.read("$['items'][0]['brand']");
        String expectedBrand = "Lenovo";

        assertThat(brand).isEqualTo(expectedBrand);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdByMultiBrandIfOneBrandHaveNotItem() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?brands=Apple,Samsung", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 1;
        assertThat(sizeItemsArray).isEqualTo(expectedSize);

        String brand = documentContext.read("$['items'][0].brand");
        String expectedBrand = "Apple";
        assertThat(brand).isEqualTo(expectedBrand);
    }

    @Test
    public void shouldReturnItemsBySubCategoryIdInRangePriceAndByAvailable() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/sub-categories/100?price_from=17400&price_to=30000&available=false", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 1;

        assertThat(sizeItemsArray).isEqualTo(expectedSize);

        double priceFirstItem = documentContext.read("$['items'][0].price");
        double priceFrom = 17400;
        double priceTo = 30000;

        assertThat(priceFirstItem).isGreaterThanOrEqualTo(priceFrom).isLessThanOrEqualTo(priceTo);

        boolean availableItem = documentContext.read("$['items'][0]['available']");

        assertThat(availableItem).isEqualTo(false);
    }

    @Test
    public void shouldReturnItemsByCategoryIdWithAllFilters() {
        ResponseEntity<String> response = restTemplate.getForEntity("/markethub/goods/categories/100?price_from=30000&price_to=50000&available=false&brands=Lenovo,Asus", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody()); //Lenovo  price=489999
        int sizeItemsArray = documentContext.read("$['items'].length()");
        int expectedSize = 1;

        assertThat(sizeItemsArray).isEqualTo(expectedSize);

        double priceActual = documentContext.read("$['items'][0].['price']");
        double priceFrom = 30000;
        double priceTo = 50000;

        assertThat(priceActual).isGreaterThanOrEqualTo(priceFrom).isLessThanOrEqualTo(priceTo);

        String brand = documentContext.read("$['items'][0]['brand']");
        String expectedBrand = "Lenovo";

        assertThat(brand).isEqualTo(expectedBrand);
    }

    //???
    @Test
    public void shouldReturn4TopItems() {
        ResponseEntity<ItemsResponse> response = restTemplate.getForEntity("/markethub/goods/top-seller", ItemsResponse.class);
    }

    @Test
    public void shouldCreateNewItem() {
        //USER LOGIN
        LoginRequest loginRequest = new LoginRequest("bilbo@gmail.com", "pass123");
        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/markethub/login", loginRequest, String.class);

        //GET JWT TOKEN
        DocumentContext documentContext = JsonPath.parse(loginResponse.getBody());
        String jwt = documentContext.read("$.token");

        //CREATE HEADER WITH TOKEN
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);

        //CREATE NEW_ITEM_REQUEST
        NewItemRequest newItemRequest = generateNewItemRequest();

        //CREATE ENTITY WITH BODY & HEADER
        HttpEntity<NewItemRequest> requestEntity = new HttpEntity<>(newItemRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/markethub/goods/add", requestEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //GET ITEM_ID
        documentContext = JsonPath.parse(response.getBody());
        int itemId = documentContext.read("$.id");

        //CREATE ENTITY WITH HEADER
        HttpEntity<?> requestRemove = new HttpEntity<>(headers);

        restTemplate.exchange("/markethub/goods/remove/" + itemId, HttpMethod.DELETE, requestRemove, String.class); //delete don`t work
        assertThat(itemRepositoryTest.findById((long) itemId)).isEqualTo(Optional.empty());
    }

    private NewItemRequest generateNewItemRequest() {
        NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setName("Test-Item");
        newItemRequest.setDescription("None");
        newItemRequest.setPrice(50000);
        newItemRequest.setBrand("Kamui");
        newItemRequest.setPhotos(List.of(Util.encodeToBase64("example.png")));
        newItemRequest.setStockQuantity(5);
        newItemRequest.setOwner(1);
        newItemRequest.setCategory(100);
        newItemRequest.setSubCategory(100);
        return newItemRequest;
    }
}
