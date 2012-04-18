package org.eatbacon.mongodb.benchmark;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.eatbacon.mongodb.benchmark.jackson.entity.TestObject;

import com.mongodb.DBObject;

public class JacksonBenchmark extends RawBenchmark {
	private static ObjectMapper JACKSON_MAPPER;

	public static void main(String... args) {
		if (!parseArgs(args)) {
			usage();
			return;
		}

		JacksonBenchmark b = new JacksonBenchmark();
		try {
			b.getMapper();
			b.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deserialize(DBObject obj) {
		try {
			getMapper().readValue(obj.toString(), TestObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObjectMapper getMapper() {
		if (JACKSON_MAPPER == null) {
			AnnotationIntrospector jackson = new JacksonAnnotationIntrospector();
			AnnotationIntrospector jaxb = new JaxbAnnotationIntrospector();
			AnnotationIntrospector pair = new AnnotationIntrospector.Pair(jaxb,
					jackson);
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().setAnnotationIntrospector(jaxb);
			mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
			mapper.getDeserializationConfig().set(Feature.AUTO_DETECT_SETTERS,
					true);
			mapper.configure(Feature.AUTO_DETECT_SETTERS, true);
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(
					SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
					false);
			JACKSON_MAPPER = mapper;
		}
		return JACKSON_MAPPER;
	}
}
