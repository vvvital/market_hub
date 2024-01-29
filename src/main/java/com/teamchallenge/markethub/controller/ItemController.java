package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/goods")
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/top-seller")
    public ResponseEntity<List<ItemResponse>> getTopSellerList() throws ItemNotFoundException {
        ItemResponse item = itemService.findItemById(100);
        ItemResponse item2 = itemService.findItemById(128);
        ItemResponse item3 = itemService.findItemById(153);
        ItemResponse item4 = itemService.findItemById(178);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/shares")
    public ResponseEntity<List<ItemResponse>> getShares() throws ItemNotFoundException {
        ItemResponse item = itemService.findItemById(203);
        ItemResponse item2 = itemService.findItemById(228);
        ItemResponse item3 = itemService.findItemById(253);
        ItemResponse item4 = itemService.findItemById(278);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

}
