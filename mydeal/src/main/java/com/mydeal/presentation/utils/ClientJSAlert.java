package com.mydeal.presentation.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ClientJSAlert {
    public static void showAlert(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(String.format("<script>alert('%s');</script>", message));
    }
}
