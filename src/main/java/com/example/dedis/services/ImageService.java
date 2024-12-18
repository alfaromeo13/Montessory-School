package com.example.dedis.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        //below line sends file to the bucket
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        var f = fileObj.delete();
        // we return URl of created S3 file object
        return "https://" + bucketName + ".s3.eu-north-1.amazonaws.com/"+fileName;
    }

    public void deleteFiles(List<String> fileNames) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (String fileNameEntry : fileNames) {
                // Parse each entry in the list as a JSON array
                List<String> urls = objectMapper.readValue(fileNameEntry, new TypeReference<>() {});

                // Iterate over the extracted URLs and delete each file
                for (String url : urls) {
                    var URL = new URL(url);
                    s3Client.deleteObject(bucketName,URL.getFile().substring(1));
                }
            }
        } catch (MalformedURLException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}