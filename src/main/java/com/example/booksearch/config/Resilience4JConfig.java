package com.example.booksearch.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customCircuitBreakerFactory() {
        // Circuit Breaker 설정
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(5) // 실패율 임계값: 5%
                .waitDurationInOpenState(Duration.ofSeconds(5)) // Open 상태 지속 시간: 5초
                .slidingWindowSize(10) // 슬라이딩 윈도우 크기: 최근 10번 호출 기준
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // 요청 개수 기반
                .build();

        // Time Limiter 설정
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4)) // 타임아웃 시간: 4초
                .build();

        // Resilience4J Circuit Breaker Factory에 설정 적용
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }
}
