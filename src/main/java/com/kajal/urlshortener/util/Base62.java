package com.kajal.urlshortener.util;

public final class Base62 {

    private static final String CHARS
            = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Base62() {
    }

    public static String encode(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be non-negative");
        }
        if (id == 0) {
            return "0";
        }

        long n = id;
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(CHARS.charAt((int) (n % 62)));
            n /= 62;
        }
        return sb.reverse().toString();
    }

}
