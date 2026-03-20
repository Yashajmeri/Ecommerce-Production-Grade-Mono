package com.example.ecommerce.Project1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Represents the app config component.
 */
@Configuration
public class AppConfig {
  /**
   * Executes model mapper.
   * @return the result of model mapper.
   */
  @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
