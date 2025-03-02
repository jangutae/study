package com.example.study2.basic1.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3ImageService {

	private static final String[] WHITE_LIST = {"jpg", "jpeg", "png"};

	private static final String IMAGE_URL_PATH = "image/";

	private final AmazonS3 amazonS3;
	private final AmazonS3Client s3Client;

	@Value("${AWS_S3_BUCKET_NAME}")
	private String bucket;


	//
	public String upload(MultipartFile image) {
		// 일력 받은 이미지 파일이 빈 파일인지 검증
		if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
			throw new AmazonS3Exception("Invalid image file");
		}
		// uploadImage 를 호출하여 s3에 저장된 이미지의 public url 을 반환한다.
		return this.uploadImage(image);
	}

	private String uploadImage(MultipartFile image) {
		this.validateImageFileExtension(image.getOriginalFilename());
		try {
			return this.uploadImageToS3(image);
		} catch (IOException e) {
			throw new AmazonS3Exception("Failed to upload image", e);
		}
	}

	private void validateImageFileExtension(String filename) {
		int lastDotIndex = filename.lastIndexOf(".");

		if (lastDotIndex == -1) {
			throw new AmazonS3Exception("No exist fileExtension");
		}

		String extension = filename.substring(lastDotIndex + 1).toLowerCase();
		if (!Arrays.asList(WHITE_LIST).contains(extension)) {
			throw new AmazonS3Exception("Invalid image fileExtension");
		}
	}

	private String getExtension(String filename) {
		int lastDotIndex = filename.lastIndexOf(".");

		if (lastDotIndex == -1) {
			throw new IllegalArgumentException("No exist fileExtension");
		}

		return filename.substring(lastDotIndex);
	}

	private String uploadImageToS3(MultipartFile image) throws IOException {
		String originalFileName = image.getOriginalFilename(); // 원본 파일 명
		// String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자 명
		String extension = getExtension(originalFileName);

		String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFileName; // 변경된 파일 명

		InputStream inputStream = image.getInputStream();

		// image 를 byte[] 로 변환
		byte[] bytes = IOUtils.toByteArray(inputStream);

		ObjectMetadata objectMetadata = new ObjectMetadata(); // metadata 생성
		objectMetadata.setContentType(IMAGE_URL_PATH + extension);
		objectMetadata.setContentLength(bytes.length);


		// 	S3에 요청할 때 사용할 byteInputStream 생성
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		try {
			//S3로 putObject 할 때 사용할 요청 객체
			//생성자 : bucket 이름, 파일 명, byteInputStream, metadata
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3FileName, byteArrayInputStream,
				objectMetadata);
				// .withCannedAcl(CannedAccessControlList.PublicRead);

			// 실제 s3에 이미지 데이터를 넣는 부분이다.
			amazonS3.putObject(putObjectRequest);
		} catch (AmazonS3Exception e) {
			throw new AmazonS3Exception("Failed to upload image", e);
		} finally {
			byteArrayInputStream.close();
			inputStream.close();
		}

		return s3Client.getResourceUrl(bucket, s3FileName);
	}

	public void deleteImageFromS3(String imageAddress) {
		String key = getKeyFromImageAddress(imageAddress);

		try {
			amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
		} catch (AmazonS3Exception e) {
			throw new AmazonS3Exception("Failed to delete image", e);
		}
	}

	private String getKeyFromImageAddress(String imageAddress) {
		try {
			URL url = new URL(imageAddress);
			String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
			return decodingKey.substring(1); // 맨 앞에 "/"  제거
		} catch (MalformedURLException e) {
			throw new AmazonS3Exception("Invalid image address", e);
		}
	}

	// // 여러장의 파일 저장
	// public List<String> saveFiles(List<MultipartFile> multipartFiles) {
	// 	// Url List 생성
	// 	List<String> uploadedUrls = new ArrayList<>();
	//
	// 	// multipartFile List 에 하나씩 저장
	// 	for (MultipartFile multipartFile : multipartFiles) {
	//
	// 		if (isDuplicate(multipartFile)) {
	// 			throw new RuntimeException("Duplicate file");
	// 		}
	// 		String uploadedUrl = saveFile(multipartFile);
	// 		uploadedUrls.add(uploadedUrl);
	// 	}
	//
	// 	clear();
	// 	return uploadedUrls;
	// }
	//
	// // 파일 삭제
	// public void deleteFile(String fileUrl) {
	// 	String[] urlParts  = fileUrl.split("/");
	// 	String fileBucket = urlParts[2].split("\\.")[0];
	//
	// 	if (!fileBucket.equals(bucket)) {
	// 		throw new RuntimeException("Invalid bucket");
	// 	}
	//
	// 	String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 3, urlParts.length));
	//
	// 	if (!s3Client.doesObjectExist(bucket, objectKey)) {
	// 		throw new RuntimeException("Object does not exist");
	// 	}
	//
	// 	try {
	// 		s3Client.deleteObject(bucket, objectKey);
	// 	} catch (AmazonS3Exception e) {
	// 		throw new RuntimeException("Error deleting object", e);
	// 	} catch (SdkClientException e) {
	// 		throw new RuntimeException("Error deleting object", e);
	// 	}
	// }
	//
	// // 단일 파일 저장\
	// public String saveFile(MultipartFile file) {
	// 	String randomFileName = generateRandomFilename(file);
	//
	// 	ObjectMetadata metadata = new ObjectMetadata();
	// 	metadata.setContentLength(file.getSize());
	// 	metadata.setContentType(file.getContentType());
	//
	// 	try {
	// 		s3Client.putObject(bucket, randomFileName, file.getInputStream(), metadata);
	// 	}  catch (AmazonS3Exception e) {
	// 		throw new RuntimeException("file upload failed", e);
	// 	} catch (SdkClientException e) {
	// 		throw new RuntimeException("file upload failed", e);
	// 	} catch (IOException e) {
	// 		throw new RuntimeException("file upload failed", e);
	// 	}
	//
	// 	return s3Client.getUrl(bucket, randomFileName).toString();
	// }
	//
	// private boolean isDuplicate(MultipartFile file) {
	// 	String fileName = file.getOriginalFilename();
	// 	Long fileSize = file.getSize();
	//
	// 	if (uploadedFileNames.contains(fileName) && uploadedFileSizes.contains(fileSize)) {
	// 		return true;
	// 	}
	//
	// 	uploadedFileNames.add(fileName);
	// 	uploadedFileSizes.add(fileSize);
	//
	// 	return false;
	// }
	//
	// private void clear() {
	// 	uploadedFileNames.clear();
	// 	uploadedFileSizes.clear();
	// }
	//
	// // 랜덤파일명 생성
	// private String generateRandomFilename(MultipartFile file) {
	// 	String originalFileName = file.getOriginalFilename();
	// 	String fileExtension = validateFileExtension(originalFileName);
	// 	String randomFilename = UUID.randomUUID() + "." + fileExtension;
	// 	return randomFilename;
	// }
	//
	// // 파일 확장자 체크
	// private String validateFileExtension(String originalFileName) {
	// 	String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
	// 	List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");
	//
	// 	if (!allowedExtensions.contains(fileExtension)) {
	// 		throw new RuntimeException("Invalid file format");
	// 	}
	// 	return fileExtension;
	// }
}
