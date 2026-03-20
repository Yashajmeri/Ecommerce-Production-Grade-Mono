package com.example.ecommerce.Project1.exception;

/**
 * Represents the resource not found exception component.
 */
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String field;
    Long fieldId;
     /**
      * Creates a new `ResourceNotFoundException` instance.
      */
     public ResourceNotFoundException() {}
    /**
     * Creates a new `ResourceNotFoundException` instance.
     * @param resourceName the resourceName value.
     * @param fieldName the fieldName value.
     * @param field the field value.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, String field) {
         super(String.format("%s not found with %s:%s",resourceName,field,fieldName));
      this.resourceName = resourceName;
      this.fieldName = fieldName;
      this.field = field;
    }
    /**
     * Creates a new `ResourceNotFoundException` instance.
     * @param resourceName the resourceName value.
     * @param field the field value.
     * @param fieldId the fieldId value.
     */
    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s:%d",resourceName,field,fieldId));
         this.resourceName = resourceName;
         this.fieldId = fieldId;
         this.field = field;
    }
}
