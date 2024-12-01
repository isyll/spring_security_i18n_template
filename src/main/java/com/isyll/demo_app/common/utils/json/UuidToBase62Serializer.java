package com.isyll.demo_app.common.utils.json;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.isyll.demo_app.common.utils.IdUtils;

public class UuidToBase62Serializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(IdUtils.convertUuidToBaseId(UUID.fromString(value)));
	}
}
