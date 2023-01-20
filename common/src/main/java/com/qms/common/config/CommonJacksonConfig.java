package com.qms.common.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * configure jackson properties to use for entire application (for mapping
 * to/from json) by creating bean of Jackson2ObjectMapperBuilderCustomizer
 * 
 * for eg: we can specify date format, include properties etc. for all our
 * application instead of using @JsonFormat, @JsonInclude etc. annotation
 * everytime
 * 
 * @author Rahul
 *
 */
@Configuration
public class CommonJacksonConfig {

	private static final String dateFormat = "MM-dd-yyyy";
	private static final String dateTimeFormat = "MM-dd-yyyy HH:mm:ss";

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return jacksonObjectMapperBuilder -> {
			jacksonObjectMapperBuilder.simpleDateFormat(dateTimeFormat);
			jacksonObjectMapperBuilder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
			jacksonObjectMapperBuilder
					.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
		};
	}
}
