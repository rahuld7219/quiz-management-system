package com.qms.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	// To communicate to the redis server via the connection made(by properties
	// config) we need RedisTemplate, spring automatically pass the
	// redisConnectionFactory created using properties config
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// we use StringRedisSerializer() for key,
		// so keys on redis shown as string
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		// we use JdkSerializationRedisSerializer for value,
		// so values on redis will be shown in
		// some hexa unreadable form,
		// we can use StringRedisSerializer also
		// if we want values to be shown as string on redis,
		// there are other options also for serialization
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		return redisTemplate;
	}
}
