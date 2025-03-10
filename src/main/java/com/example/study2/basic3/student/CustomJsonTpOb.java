package com.example.study2.basic3.student;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJsonTpOb {
	public static void main(String[] args) {

		try {
			String json = "{\"id\" : 44555, \"name\" : \"김일반\"}";
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule("CustomStudentDeserializer",
				new Version(1, 0, 0, null, null, null));
			module.addDeserializer(Student.class, new CustomStudentDeserializer());
			mapper.registerModule(module);
			Student student = mapper.readValue(json, Student.class);
			System.out.println(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
