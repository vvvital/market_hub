package com.teamchallenge.markethub.dto.item;

import com.teamchallenge.markethub.model.Photo;

public record PhotoResponse(String photo_url) {
    public static PhotoResponse convertToPhoto(Photo photo) {
        return new PhotoResponse(photo.getUrl());
    }
}
