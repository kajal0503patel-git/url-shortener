package com.kajal.urlshortener.service;

import com.kajal.urlshortener.model.UrlMapping;
import com.kajal.urlshortener.repository.UrlMappingRepository;
import com.kajal.urlshortener.util.Base62;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {

    private final UrlMappingRepository repository;

    public UrlService(UrlMappingRepository repository) {
        this.repository = repository;
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

        return code;
    }

    public Optional<String> resolve(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return repository.findByShortCode(code).map(UrlMapping::getLongUrl);
    }
}
