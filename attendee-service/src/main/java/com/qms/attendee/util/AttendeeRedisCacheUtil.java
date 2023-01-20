package com.qms.attendee.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qms.attendee.constant.AttendeeMessageConstant;
import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.exception.custom.QuizSubmissionNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AttendeeRedisCacheUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

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
	 * @param quizSubmission
	 */
	public void cacheSubmission(String key, QuizSubmission quizSubmission) {
		try {
			String payload = objectMapper.writeValueAsString(quizSubmission);
			redisTemplate.opsForValue().set(key, payload);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public QuizSubmission getCachedSubmission(String key) {
		try {
			return objectMapper.readValue((String) redisTemplate.opsForValue().get(key), QuizSubmission.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw new QuizSubmissionNotFoundException(AttendeeMessageConstant.QUIZ_SUBMISSION_NOT_FOUND);
		}
		return null;
	}

}
