package com.mydeal.presentation.controllers.frontcontroller.viewresolve;

import lombok.Data;

@Data
public class ViewResolver {

    private String view;
    private ResolveAction resolveAction;

    public ViewResolver(){

    }
    public ViewResolver(final String view){
        this.view = view;
        resolveAction = ResolveAction.FORWARD;
    }
    public  void forward(final String view){
        setView(view);
        resolveAction = ResolveAction.FORWARD;
    }
    public void redirect(final String view){
        setView(view);
        resolveAction = ResolveAction.REDIRECT;
    }


}
