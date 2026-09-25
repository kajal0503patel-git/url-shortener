package com.kajal.urlshortener.service;

/*
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlServiceTest {

    private UrlService service;

    @BeforeEach
    void setUp() {
        service = new UrlService();
    }

    @Test
    void createReturnsNonEmptyCode() {
        String code = service.create("https://example.com/a");

        assertNotNull(code);
        assertFalse(code.isEmpty());
    }

    @Test
    void sameUrlGivesSameCode() {
        String first = service.create("https://example.com/a");
        String second = service.create("https://example.com/a");

        assertEquals(first, second);
    }

    @Test
    void differentUrlsGiveDifferentCodes() {
        String codeA = service.create("https://example.com/a");
        String codeB = service.create("https://example.com/b");

        assertNotEquals(codeA, codeB);
    }

    @Test
    void resolveReturnsOriginalUrl() {
        String url = "https://example.com/a";
        String code = service.create(url);

        assertEquals(url, service.resolve(code).orElseThrow());
    }

    @Test
    void resolveUnknownCodeReturnsEmpty() {
        assertTrue(service.resolve("zzzzz").isEmpty());
    }

    @Test
    void createNullUrlThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createBlankUrlThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.create("   "));
    }

    @Test
    void resolveNullCodeReturnsEmpty() {
        assertTrue(service.resolve(null).isEmpty());
    }

    @Test
    void concurrentCreatesProduceUniqueCodes() throws Exception {
        int n = 1000;
        ExecutorService pool = Executors.newFixedThreadPool(16);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String url = "https://example.com/" + i;
                futures.add(pool.submit(() -> service.create(url)));
            }
            Set<String> codes = new HashSet<>();
            for (Future<String> f : futures) {
                codes.add(f.get());
            }
            assertEquals(n, codes.size());
        } finally {
            pool.shutdown();
        }
    }

    @Test
    void concurrentCreatesOfSameUrlGiveOneCode() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(16);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                futures.add(pool.submit(() -> service.create("https://example.com/same")));
            }
            Set<String> codes = new HashSet<>();
            for (Future<String> f : futures) {
                codes.add(f.get());
            }
            assertEquals(1, codes.size());
        } finally {
            pool.shutdown();
        }
    }
}
*/
