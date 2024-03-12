package com.mydeal.presentation.controllers.frontcontroller;

import com.mydeal.presentation.controllers.LoginServlet;
import com.mydeal.presentation.controllers.RegisterServlet;

public class ControllerFactory {

    public ControllerFactory(){

    }
    public Controller getController(final String controllerName){
        switch (controllerName){
            case "register":
                return new RegisterServlet();
            case "login":
                return new LoginServlet();
            default:
        }
        return null;
    }
}
