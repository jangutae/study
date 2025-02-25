package com.example.study2.s3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${AWS_IAM_ACCESS_KEY}")
	private String accessKey;

	@Value("${AWS_IAM_SECRET_KEY}")
	private String secretKey;

	@Value("ap-northeast-2")
	private String region;

	// @Bean
	// public AmazonS3 amazons3() {
	// 	AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	//
	// 	return AmazonS3ClientBuilder
	// 		.standard()
	// 		.withCredentials(new AWSStaticCredentialsProvider(credentials))
	// 		.withRegion(region)
	// 		.build();
	// }

	@Bean
	public AmazonS3Client s3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		return (AmazonS3Client) AmazonS3ClientBuilder
			.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
	}
}
