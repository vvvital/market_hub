package com.teamchallenge.markethub.dto.item.detail;

import com.teamchallenge.markethub.model.Photo;

public record PhotoDetails(String photoUrl) {
    public static PhotoDetails convertToPhoto(Photo photo) {
        return new PhotoDetails(photo.getUrl());
    }
}
