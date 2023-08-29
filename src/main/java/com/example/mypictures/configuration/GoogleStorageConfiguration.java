package com.example.mypictures.configuration;

import com.example.mypictures.constant.GoogleCloudConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;


public class GoogleStorageConfiguration {

    private Resource credentialsResource;


    public Storage storage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsResource.getInputStream());
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        return options.getService();
    }
}
