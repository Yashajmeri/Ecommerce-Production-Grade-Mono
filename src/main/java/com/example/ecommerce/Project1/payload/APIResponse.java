package com.example.ecommerce.Project1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the api response component.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
   public String message;
   private boolean status;
}
