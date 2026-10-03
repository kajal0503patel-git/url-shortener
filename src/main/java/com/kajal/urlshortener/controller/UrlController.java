package com.kajal.urlshortener.controller;

import com.kajal.urlshortener.dto.CreateUrlRequest;
import com.kajal.urlshortener.dto.CreateUrlResponse;
import com.kajal.urlshortener.service.RateLimiterService;
import com.kajal.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService service;
    private final RateLimiterService rateLimiterService;

    public UrlController(UrlService service, RateLimiterService rateLimiterService) {
        this.service = service;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/api/urls")
    public ResponseEntity<?> create(@RequestBody CreateUrlRequest request, HttpServletRequest httpRequest) {
        String clientIp = httpRequest.getRemoteAddr();

        if (!rateLimiterService.isAllowed(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. Try again later.");
        }

        String code = service.create(request.longUrl());
        String baseUrl = httpRequest.getScheme() + "://" + httpRequest.getServerName();
        int port = httpRequest.getServerPort();
        if (port != 80 && port != 443) {
            baseUrl += ":" + port;
        }
        String shortUrl = baseUrl + "/" + code;
        return ResponseEntity.ok(new CreateUrlResponse(code, shortUrl));
    }
}
