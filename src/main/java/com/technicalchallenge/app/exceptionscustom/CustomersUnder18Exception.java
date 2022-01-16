package com.technicalchallenge.app.exceptionscustom;

public class CustomersUnder18Exception  extends RuntimeException{
    public CustomersUnder18Exception(String message) {
        super(message);
    }
}
