package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.Photo;

import java.util.List;

public interface PhotoService {
    void create(Photo photo);
    List<Photo> convertBase64ListToPhotoList(List<String> base64Strings, String directoryName, Item item);
}
