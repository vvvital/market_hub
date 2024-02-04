package com.teamchallenge.markethub.dto.item;

import com.teamchallenge.markethub.dto.item.detail.ItemDetails;

import java.util.List;

public record ItemResponse(int size, List<ItemDetails> items) {
}
