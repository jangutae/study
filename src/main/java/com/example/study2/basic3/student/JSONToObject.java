package com.example.study2.basic3.student;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JSONToObject {

	public static void main(String[] args) {

		try {
			String jsonString = "{\"id\" : 1, \"name\" : \"김일반\"}";

			ObjectMapper mapper = new ObjectMapper();

			Student student = mapper.readValue(jsonString, Student.class);

			System.out.println(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
