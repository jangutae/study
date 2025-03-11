package com.example.study2.basic3.student;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObToJSON {
	public static void main(String[] args) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule("CustomStudentSerializer",
				new Version(1, 0, 0, null, null, null));
			module.addSerializer(Student.class, new CustomStudentSerializer());
			mapper.registerModule(module);
			Student student = new Student(46L, "선생님");

			String studentJson = mapper.writerWithDefaultPrettyPrinter()
									.writeValueAsString(student);
			System.out.println(studentJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
