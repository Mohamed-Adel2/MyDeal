package com.mydeal.domain.util;

public class AppConfig {
    public static String DB_HOST;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static void load() {
        DB_USER = System.getenv("root");
        DB_PASSWORD = System.getenv("1192001rrrrr");
    }
}
