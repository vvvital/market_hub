package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.error.exception.InvalidFormatBase64;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.Photo;
import com.teamchallenge.markethub.repository.PhotoRepository;
import com.teamchallenge.markethub.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PhotoServiceImpl implements PhotoService {
    private static final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;

    @Value("${storage.path}")
    private String path;
    @Value("${endpoint.photo}")
    private String endpoint;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void create(Photo photo) {
        photoRepository.save(photo);
    }

    @Transactional
    @Override
    public List<Photo> convertBase64ListToPhotoList(List<String> base64Strings, String directoryName, Item item) {
        List<Photo> photoList = new ArrayList<>(4);
        List<PhotoBuffer> photoBuffers = new ArrayList<>(4);
        String storagePath = path + directoryName + "/";

        for (String str : base64Strings) {
            Photo photo = new Photo();
            photoList.add(photoRepository.save(photo));

            //get info about photo (name, byte[])
            PhotoBuffer photoInfo = decodeBase64ToPhoto(str, photo.getId().toString());
            photoBuffers.add(photoInfo);

            //update photo record
            photoRepository.save(updatePhoto(directoryName, item, photo, photoInfo.filename));
        }
        savePhotosInStorage(photoBuffers, storagePath);
        return photoList;
    }

    private PhotoBuffer decodeBase64ToPhoto(String base64, String name) {
        String[] parser = base64.split(";");
        String format = Objects.equals(parser[0], "data:image/jpeg;base64") ? "jpg" : "png";

        String filename = name + "." + format;
        byte[] buffer = decodeBase64toPhoto(parser[1]);

        return new PhotoBuffer(filename, buffer);
    }

    private byte[] decodeBase64toPhoto(String str) {
        try {
            String s = str.substring(7);
            return Base64.getDecoder().decode(s);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatBase64();
        }
    }

    private Photo updatePhoto(String directoryName, Item item, Photo photo, String name) {
        photo.setName(name);
        photo.setItem(item);
        photo.setUrl(String.format("%s/%s/%s", endpoint, directoryName, name));
        return photo;
    }

    private void savePhotosInStorage(List<PhotoBuffer> photos, String storagePath) {
        for (PhotoBuffer photo : photos) {
            Path path = Paths.get(storagePath, photo.filename);
            writeInStorage(path, photo.buffer);
        }
    }

    private void writeInStorage(Path path, byte[] buffer) {
        try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
            outputStream.write(buffer);
        } catch (IOException e) {
            log.error("Failed save photo {}", e.getMessage());
        }
    }

    private record PhotoBuffer(String filename, byte[] buffer) {

    }

    //    private void removePhotos(String storagePath, List<String> names) {
//        try {
//            for (String name : names) {
//                Files.deleteIfExists(Paths.get(storagePath, name));
//            }
//        } catch (IOException e) {
//            log.error("Failed to delete photo {}", e.getMessage());
//        }
//    }
}