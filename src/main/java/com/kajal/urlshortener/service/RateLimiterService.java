package com.kajal.urlshortener.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String clientIp) {
        String key = "rate_limit:" + clientIp;

        try {
            Long count = redisTemplate.opsForValue().increment(key);

            if (count != null && count == 1) {
                redisTemplate.expire(key, WINDOW);
            }

            return count != null && count <= MAX_REQUESTS;
        } catch (Exception e) {
            System.out.println("Rate limiter unavailable, allowing request: " + e.getMessage());
            return true;
        }
    }
}
