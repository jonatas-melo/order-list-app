package com.jonatasmelo.orderlistapp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class HelloWorld {

    public String getMessage() {
        return "Hello World!";
    }

}
