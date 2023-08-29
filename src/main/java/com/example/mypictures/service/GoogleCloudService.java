package com.example.mypictures.service;

import com.example.mypictures.constant.AlbumConstants;
import com.example.mypictures.constant.GoogleCloudConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class GoogleCloudService {
    private final Storage storage;

    public GoogleCloudService() throws IOException {
        String credentialsPath = "src/main/resources/credentials/" + GoogleCloudConstants.JSON_KEY_NAME;
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        this.storage = options.getService();
    }

    public void uploadPhoto(String fileName, MultipartFile file) {
        BlobId blobId = BlobId.of(GoogleCloudConstants.BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        try {
            storage.create(blobInfo, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePhoto(String fileName) {
        BlobId blobId = BlobId.of(GoogleCloudConstants.BUCKET_NAME, fileName);
        storage.delete(blobId);
    }

    public String getPhotoURL(String fileName) {
        if (fileName.equals(AlbumConstants.DEFAULT_COVER_LOCATION))
            return AlbumConstants.DEFAULT_COVER_LOCATION;
        return "https://storage.googleapis.com/" + GoogleCloudConstants.BUCKET_NAME + "/" + fileName;
    }
}
