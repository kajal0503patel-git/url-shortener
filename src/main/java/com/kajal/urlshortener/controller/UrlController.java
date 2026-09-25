package com.kajal.urlshortener.controller;

import com.kajal.urlshortener.dto.CreateUrlRequest;
import com.kajal.urlshortener.dto.CreateUrlResponse;
import com.kajal.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/api/urls")
    public CreateUrlResponse create(@RequestBody CreateUrlRequest request) {
        String code = service.create(request.longUrl());
        String shortUrl = "http://localhost:8080/" + code;
        return new CreateUrlResponse(code, shortUrl);
    }
}
