package com.project.userapp.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

//@Service
public class AWSService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public AWSService(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    // 파일 업로드
    public String uploadFile(File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, file.getName(), file));
        return "File uploaded successfully: " + file.getName();
    }
}
