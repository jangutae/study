package com.example.study2.basic3.student;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJSON {
	public static void main(String[] args) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			Student student = new Student(1L, "김일반");

			String jsonString = mapper.writerWithDefaultPrettyPrinter()
									.writeValueAsString(student);

			System.out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
