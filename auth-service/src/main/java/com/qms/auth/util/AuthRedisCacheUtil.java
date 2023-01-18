package com.qms.auth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthRedisCacheUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	// TODO: change name for methods for access tokens

	/**
	 * Get value for given key
	 * 
	 * @param key
	 */
	public String getCachedValue(String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}

	/**
	 * Set the value of a key
	 * 
	 * @param key
	 * @param value
	 */
	public void cacheValue(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 
	 * @param key
	 */
	public void removeCachedValue(String key) {

	}

}
