package com.musicbook.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.musicbook.entity.Artist;

public class ArtistSerializer extends JsonSerializer<Artist> {

	@Override
	public void serialize(Artist artist, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", artist.getName());
		jsonGenerator.writeEndObject();
	}
}
