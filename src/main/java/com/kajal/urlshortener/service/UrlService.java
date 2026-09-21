package com.kajal.urlshortener.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.kajal.urlshortener.util.Base62;

public class UrlService {

    private final AtomicLong counter = new AtomicLong(1);
    private final ConcurrentHashMap<String, String> codeToUrl = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> urlToCode = new ConcurrentHashMap<>();

    public String create(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            throw new IllegalArgumentException("longUrl must not be null or blank");
        }
        return urlToCode.computeIfAbsent(longUrl, url -> {
            long id = counter.getAndIncrement();
            String code = Base62.encode(id);
            codeToUrl.put(code, url);
            return code;
        });
    }

    public Optional<String> resolve(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(codeToUrl.get(code));
    }
}
