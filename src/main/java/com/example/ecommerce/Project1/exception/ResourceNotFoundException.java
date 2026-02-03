package com.example.ecommerce.Project1.exception;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String field;
    Long fieldId;
     public ResourceNotFoundException() {}
    public ResourceNotFoundException(String resourceName, String fieldName, String field) {
         super(String.format("%s not found with %s:%s",resourceName,field,fieldName));
      this.resourceName = resourceName;
      this.fieldName = fieldName;
      this.field = field;
    }
    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s:%d",resourceName,field,fieldId));
         this.resourceName = resourceName;
         this.fieldId = fieldId;
         this.field = field;
    }
}
