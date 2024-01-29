package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;

public interface ItemService {
    ItemResponse findItemById(long id) throws ItemNotFoundException;
}
