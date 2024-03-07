package com.teamchallenge.markethub.dto.item;

import java.util.List;

public record ItemsResponse(int size, List<ItemResponse> items) {
}
