package com.teamchallenge.markethub.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.teamchallenge.markethub.dto.login.LoginRequest;
import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.model.enums.DeliveryService;
import com.teamchallenge.markethub.model.enums.Status;
import com.teamchallenge.markethub.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void shouldCreateNewOrder() {
        //USER LOGIN
        LoginRequest loginRequest = new LoginRequest("bilbo@gmail.com", "pass123");
        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/markethub/login", loginRequest, String.class);

        //GET JWT TOKEN
        DocumentContext documentContext = JsonPath.parse(loginResponse.getBody());
        String jwt = documentContext.read("$.token");

        //CREATE HEADER WITH TOKEN
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + jwt);

        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                .customerFirstname("Bilbo")
                .customerLastname("Baggins")
                .customerEmail("bilbo@gmail.com")
                .customerPhone("380943234149")
                .customerCity("Kyiv")
                .items(List.of(new CreateOrderRequest.DateAboutTheOrderedItem(100L, 1)))
                .deliveryService(DeliveryService.NOVA_POSHTA)
                .postalAddress("address")
                .build();

        //CREATE ENTITY WITH BODY & HEADER
        HttpEntity<CreateOrderRequest> requestEntity = new HttpEntity<>(orderRequest, header);

        ResponseEntity<String> response = restTemplate.postForEntity("/markethub/orders",requestEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(response.getBody());
        int orderId = documentContext.read("$.order_number");

        //CHANGE ORDER STATUS
        HttpEntity<OrderStatusChangeRequest> requestChangeStatus = new HttpEntity<>( new OrderStatusChangeRequest(Status.CANCELED), header);
        restTemplate.exchange("/markethub/orders/" + orderId, HttpMethod.PUT, requestChangeStatus, String.class);
        assertThat(orderRepository.findById((long) orderId).orElseThrow().getStatus()).isEqualTo(Status.CANCELED);

        //REMOVE ORDER
        HttpEntity<?> requestRemove = new HttpEntity<>(header);
        restTemplate.exchange("/markethub/orders/" + orderId, HttpMethod.DELETE, requestRemove, String.class);
        assertThat(orderRepository.findById((long) orderId)).isEqualTo(Optional.empty());
    }
}
