package com.example.ecommerce.Project1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the address component.
 */
@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="addressId")
    private Long addressId;
    @Size(min=5, message = "Street name must be at least 5 characters ")
    private String street;
    @Size(min=5, message = "Building name must be at least 5 characters ")
    private String buildingName;
    @Size(min=4, message = "City name must be at least 4 characters ")
    private String city;
    @Size(min=2, message = "State name must be at least 2 characters ")
    private String state;
    @Size(min=2, message = "Country name must be at least 2 characters ")
    private String country;
    @Size(min=6, message = "Pin code  must be at least 6 characters ")
    private String pincode;

    /**
     * Creates a new `Address` instance.
     * @param street the street value.
     * @param buildingName the buildingName value.
     * @param city the city value.
     * @param state the state value.
     * @param country the country value.
     * @param pincode the pincode value.
     */
    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


}
