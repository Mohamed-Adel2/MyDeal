package com.mydeal.domain.util;

public class AppConfig {
    public static String DB_HOST;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static void load() {
        // ** Rashida ** note that this line result null value , i don't understand why !!
        DB_USER = System.getenv("root");
        System.out.println("DB_USER : " + DB_USER);
        DB_PASSWORD = System.getenv("1234");
        System.out.println("DB_PASSWORD : " + DB_PASSWORD);
    }
}
