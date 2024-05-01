package com.airbnb.securityConfig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.hibernate.annotations.Bag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class AWSS3Config {
    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${region}")
    private String region;
    @Bean
    public AWSCredentials credentials(){
        AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);
        return credentials;
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentials credentials){
        AmazonS3 s3client= AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
        return s3client;
    }
}
