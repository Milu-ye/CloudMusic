package com.eren.cloudmusic.account.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfig {

    @Bean
    MinioClient minioClient() throws Exception {
         MinioClient minioClient = MinioClient.builder()
                        .endpoint("http://127.0.0.1:9000")
                        .credentials("minioadmin", "minioadmin")
                        .build();
         if (minioClient.bucketExists(BucketExistsArgs.builder().bucket("account-avatar").build())){
             minioClient.makeBucket(MakeBucketArgs.builder().bucket("account-avatar").build());
         }
         return minioClient;
     }
}
