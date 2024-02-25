package com.teamchallenge.markethub.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${bucket.accessKey}")
    String accessKey;
    @Value("${bucket.secretKey}")
    String secretKey;
    String endpointUrl = "https://s3.tebi.io";
    String region = "eu-central-1";

    @Bean
    public BasicAWSCredentials credentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3(BasicAWSCredentials credentials) {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .enablePathStyleAccess()
                .build();
    }
}
