package com.example.ecommerce.Project1.exception;

import com.example.ecommerce.Project1.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

/**
 * Represents the my global exception handler component.
 */
public class MyGlobalExceptionHandler {
  /**
   * Executes my method argument not valid exception.
   * @param e the e value.
   * @return the result of my method argument not valid exception.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldName = ((FieldError)error).getField();
           String errorMessage = error.getDefaultMessage();
           response.put(fieldName, errorMessage);
        } );
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
  }
  /**
   * Executes my resource not found exception.
   * @param e the e value.
   * @return the result of my resource not found exception.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e) {
      String message = e.getMessage();
      APIResponse ApiResponse = new APIResponse(message,false);
      return new ResponseEntity<>(ApiResponse, HttpStatus.NOT_FOUND);
  }
  /**
   * Executes my api exception.
   * @param e the e value.
   * @return the result of my api exception.
   */
  @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e) {
      String message = e.getMessage();
      APIResponse ApiResponse = new APIResponse(message,false);
      return new ResponseEntity<>(ApiResponse, HttpStatus.BAD_REQUEST);
  }
}
