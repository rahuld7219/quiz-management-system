//package com.qms.common.config;
//
//import java.time.OffsetDateTime;
//import java.util.Optional;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.auditing.DateTimeProvider;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//@Configuration
////@EnableTransactionManagement
//@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
//public class AppConfig {
//
//	@Bean(name = "auditingDateTimeProvider")
//	public DateTimeProvider dateTimeProvider() {
//		return () -> Optional.of(OffsetDateTime.now());
//	}
//}
