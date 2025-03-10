package com.example.study2.basic3.student;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomStudentSerializer extends StdSerializer<Student> {
	// StdSerializer 는 JSONSerializer 를 상속 받음
	public CustomStudentSerializer() {
		this(null);
	}

	public CustomStudentSerializer(Class<Student> t) {
		super(t);
	}

	@Override
	public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
		IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("age ", student.getId());
		jsonGenerator.writeStringField("teacher ", student.getName());
		jsonGenerator.writeEndObject();
	}
}
