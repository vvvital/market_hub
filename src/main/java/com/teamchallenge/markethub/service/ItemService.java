package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;

public interface ItemService {
    Item findItemById(long id) throws ItemNotFoundException;
}
