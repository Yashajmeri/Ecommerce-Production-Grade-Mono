package com.example.ecommerce.Project1.exception;

/**
 * Represents the api exception component.
 */
public class APIException  extends RuntimeException{
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new `APIException` instance.
     */
    public APIException() {}
    /**
     * Creates a new `APIException` instance.
     * @param message the message value.
     */
    public APIException(String message){
        super(message);
    }
}


