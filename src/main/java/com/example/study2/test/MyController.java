package com.example.study2.test;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/test")
public class MyController {

	final MyRepository myRepository;

	public MyController(MyRepository myRepository) {
		this.myRepository = myRepository;
	}

	@PostMapping
	public void createJwt() {
		Jut jut = new Jut(1L,"TEST");

		myRepository.save(jut);
		myRepository.findById(1L);
	}

	@GetMapping("/{id}")
	public Optional<Jut> findById(@PathVariable Long id) {
		return myRepository.findById(id);
	}

	@GetMapping
	public List<Jut> findAll() {
		return myRepository.findAll();
	}

	@PutMapping("/{id}")
	public void updateJut(
		@PathVariable Long id,
		@RequestBody JutDTO DTO
		) {
		Jut findJut = myRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("sds"));

		findJut.updateByDTO(DTO);

		myRepository.save(findJut);
	}

	@DeleteMapping("/{id}")
	public void updateJut(
		@PathVariable Long id
	) {
		Jut findJut = myRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("sds"));

		myRepository.delete(findJut);
	}
}
