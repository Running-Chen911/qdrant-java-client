package io.metaloom.qdrant.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.metaloom.qdrant.client.http.model.RestModel;
import io.metaloom.qdrant.client.http.model.collection.AliasOperation;
import io.metaloom.qdrant.client.http.model.collection.filter.condition.Condition;
import io.metaloom.qdrant.client.http.model.collection.filter.match.Match;
import io.metaloom.qdrant.client.http.model.point.NamedVector;
import io.metaloom.qdrant.client.http.model.point.Payload;
import io.metaloom.qdrant.client.http.model.service.ServiceTelemetryResponse;
import io.metaloom.qdrant.client.http.model.telemetry.CollectionTelemetry;

/**
 * Helper which manages JSON handling.
 */
public final class Json {

	public static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(Condition.class, new ConditionDeserializer());
		module.addDeserializer(Match.class, new MatchDeserializer());
		module.addDeserializer(Payload.class, new PayloadDeserializer());
		module.addSerializer(Payload.class, new PayloadSerializer());
		module.addDeserializer(NamedVector.class, new NamedVectorDeserializer());
		module.addDeserializer(CollectionTelemetry.class, new CollectionTelemetryDeserializer());
		module.addDeserializer(AliasOperation.class, new AliasOperationDeserializer());
		mapper.registerModule(module);
	}

	private Json() {
	}

	public static JsonNode toJson(String content) throws JsonMappingException, JsonProcessingException {
		JsonNode json = mapper.readTree(content);
		if (json == null) {
			return null;
		}
		return json;
	}

	public static String parse(RestModel model) {
		try {
			return mapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error while parsing model to JSON.", e);
		}
	}

	public static <T extends RestModel> T parse(String json, Class<T> modelClass) {
		try {
			return mapper.readValue(json, modelClass);
		} catch (JacksonException e) {
			throw new RuntimeException("Error while parsing model to JSON.", e);
		}
	}

}
