package com.example.study2.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3 s3Client;
	private Set<String> uploadedFileNames = new HashSet<>();
	private Set<Long> uploadedFileSizes = new HashSet<>();

	@Value("${AWS_S3_BUCKET_NAME}")
	private String bucket;

	// 여러장의 파일 저장
	public List<String> saveFiles(List<MultipartFile> multipartFiles) {
		// Url List 생성
		List<String> uploadedUrls = new ArrayList<>();

		// multipartFile List 에 하나씩 저장
		for (MultipartFile multipartFile : multipartFiles) {

			if (isDuplicate(multipartFile)) {
				throw new RuntimeException("Duplicate file");
			}
			String uploadedUrl = saveFile(multipartFile);
			uploadedUrls.add(uploadedUrl);
		}

		clear();
		return uploadedUrls;
	}

	// 파일 삭제
	public void deleteFile(String fileUrl) {
		String[] urlParts  = fileUrl.split("/");
		String fileBucket = urlParts[2].split("\\.")[0];

		if (!fileBucket.equals(bucket)) {
			throw new RuntimeException("Invalid bucket");
		}

		String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 3, urlParts.length));

		if (!s3Client.doesObjectExist(bucket, objectKey)) {
			throw new RuntimeException("Object does not exist");
		}

		try {
			s3Client.deleteObject(bucket, objectKey);
		} catch (AmazonS3Exception e) {
			throw new RuntimeException("Error deleting object", e);
		} catch (SdkClientException e) {
			throw new RuntimeException("Error deleting object", e);
		}
	}

	// 단일 파일 저장\
	public String saveFile(MultipartFile file) {
		String randomFileName = generateRandomFilename(file);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		try {
			s3Client.putObject(bucket, randomFileName, file.getInputStream(), metadata);
		}  catch (AmazonS3Exception e) {
			throw new RuntimeException("file upload failed", e);
		} catch (SdkClientException e) {
			throw new RuntimeException("file upload failed", e);
		} catch (IOException e) {
			throw new RuntimeException("file upload failed", e);
		}

		return s3Client.getUrl(bucket, randomFileName).toString();
	}

	private boolean isDuplicate(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		Long fileSize = file.getSize();

		if (uploadedFileNames.contains(fileName) && uploadedFileSizes.contains(fileSize)) {
			return true;
		}

		uploadedFileNames.add(fileName);
		uploadedFileSizes.add(fileSize);

		return false;
	}

	private void clear() {
		uploadedFileNames.clear();
		uploadedFileSizes.clear();
	}

	// 랜덤파일명 생성
	private String generateRandomFilename(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String fileExtension = validateFileExtension(originalFileName);
		String randomFilename = UUID.randomUUID() + "." + fileExtension;
		return randomFilename;
	}

	// 파일 확장자 체크
	private String validateFileExtension(String originalFileName) {
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
		List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

		if (!allowedExtensions.contains(fileExtension)) {
			throw new RuntimeException("Invalid file format");
		}
		return fileExtension;
	}
}
