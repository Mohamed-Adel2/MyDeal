package com.mydeal.presentation.controllers.frontcontroller;

import com.mydeal.presentation.controllers.frontcontroller.viewresolve.ViewResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Controller {
    ViewResolver resolve(final HttpServletRequest request, final HttpServletResponse response);
}
