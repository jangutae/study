package com.example.study2.basic1.s3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class s3Controller {

	private final S3ImageService s3ImageService;

	@PostMapping("/s3/upload")
	public ResponseEntity<?> s3Upload(
		@RequestPart(value = "image", required = false) MultipartFile image
	) {
		String profileImage = s3ImageService.upload(image);

		return ResponseEntity.ok().body(profileImage);
	}

	@GetMapping("/s3/delete")
	public ResponseEntity<?> s3delete(
		@RequestParam String addr
	) {
		s3ImageService.deleteImageFromS3(addr);
		return ResponseEntity.ok().body("삭제 완료");
	}

}
