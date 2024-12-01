package com.isyll.demo_app.common.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.isyll.demo_app.common.utils.IdUtils;

public class Base62ToUuidDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return IdUtils.convertBaseIdToUuid(p.getText()).toString();
	}
}
