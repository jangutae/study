package com.example.study2.basic3.student;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import lombok.Getter;

@Getter
public class CustomStudentDeserializer extends StdDeserializer<Student> {

	public CustomStudentDeserializer() {
		this(null);
	}

	public CustomStudentDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Student deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
		IOException,
		JacksonException {
		Student student = new Student();
		ObjectCodec codec = jsonParser.getCodec();
		JsonNode node = codec.readTree(jsonParser);

		try {
			String customName = node.get("name").asText();
			Long customId = node.get("id").asLong();
			student.updateName(customId, customName);
			return student;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
