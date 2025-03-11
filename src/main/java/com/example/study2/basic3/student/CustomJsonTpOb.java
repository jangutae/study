package com.example.study2.basic3.student;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJsonTpOb {
	public static void main(String[] args) throws Exception {

		try {
			String json = "{\"id\" : 44555, \"name\" : \"김일반\"}";
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
			System.out.println(sdf.format(new Date()));
			// SimpleModule module = new SimpleModule("CustomStudentDeserializer",
			// 	new Version(1, 0, 0, null, null, null));
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Student.class, new CustomStudentDeserializer());
			mapper.registerModule(module);
			Student student = mapper.readValue(json, Student.class);
			System.out.println(student);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}


		Student student = new Student(24L, "직렬화 테스트");
		String serialized = new ObjectMapper().writeValueAsString(student);
		System.out.println(serialized);
		System.out.println(student.getUuid());
	}
}
