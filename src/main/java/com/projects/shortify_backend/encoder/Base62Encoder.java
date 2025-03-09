package com.projects.shortify_backend.encoder;

import java.util.Random;

public class Base62Encoder {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(long num) {
        var sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }

        var shortUrl = sb.reverse().toString();

        while (shortUrl.length() < 5) {

            shortUrl = BASE62.charAt(new Random().nextInt(62)) + shortUrl;
        }

        return shortUrl;
    }
}
