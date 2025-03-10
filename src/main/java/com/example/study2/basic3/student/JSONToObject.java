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

		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule("CustomStudentSerializer",
				new Version(1, 0, 0, null, null, null));
			module.addSerializer(Student.class, new CustomStudentSerializer());
			mapper.registerModule(module);
			Student student = new Student(2L, "Serializer");

			String studentJson = mapper.writeValueAsString(student);
			System.out.println(studentJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
