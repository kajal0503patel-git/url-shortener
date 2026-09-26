package com.kajal.urlshortener.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kajal.urlshortener.model.UrlMapping;
import com.kajal.urlshortener.repository.UrlMappingRepository;
import com.kajal.urlshortener.util.Base62;

@Service
public class UrlService {

    private final UrlMappingRepository repository;
    private final RedisTemplate<String, String> redisTemplate;

    public UrlService(UrlMappingRepository repository, RedisTemplate<String, String> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    public String create(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            throw new IllegalArgumentException("longUrl must not be null or blank");
        }

        Optional<UrlMapping> existing = repository.findByLongUrl(longUrl);
        if (existing.isPresent()) {
            return existing.get().getShortCode();
        }

        UrlMapping mapping = new UrlMapping(longUrl);
        mapping = repository.save(mapping);

        String code = Base62.encode(mapping.getId());
        mapping.setShortCode(code);
        repository.save(mapping);

        cacheSafely(code, longUrl);

        return code;
    }

    public Optional<String> resolve(String code) {
        if (code == null) {
            return Optional.empty();
        }

        String cached = getFromCacheSafely(code);
        if (cached != null) {
            System.out.println("CACHE HIT for code: " + code);
            return Optional.of(cached);
        }

        System.out.println("CACHE MISS for code: " + code);
        Optional<UrlMapping> mapping = repository.findByShortCode(code);
        mapping.ifPresent(m -> cacheSafely(code, m.getLongUrl()));

        return mapping.map(UrlMapping::getLongUrl);
    }

    private void cacheSafely(String code, String longUrl) {
        try {
            redisTemplate.opsForValue().set(code, longUrl, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            System.out.println("Redis unavailable, skipping cache write: " + e.getMessage());
        }
    }

    private String getFromCacheSafely(String code) {
        try {
            return redisTemplate.opsForValue().get(code);
        } catch (Exception e) {
            System.out.println("Redis unavailable, falling back to database: " + e.getMessage());
            return null;
        }
    }
}
