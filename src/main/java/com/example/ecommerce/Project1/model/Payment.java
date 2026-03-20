package com.example.ecommerce.Project1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the payment component.
 */
@Entity
@Table(name="payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long paymentId; //  this for happening payment in this application

    @OneToOne(mappedBy = "payment",cascade ={CascadeType.PERSIST,CascadeType.PERSIST})
    private  Order order;

    @NotBlank
    @Size(min=3,  message = "Payment method must contain at least 3 characters ")
    private String paymentMethod;

    private String pgPaymentId; // this is the id for happening payment in payment fate way
    private String pgStatus;
    private String pgResponseMessage;
    private String pgName;

  /**
   * Creates a new `Payment` instance.
   * @param paymentMethod the paymentMethod value.
   * @param pgPaymentId the pgPaymentId value.
   * @param pgStatus the pgStatus value.
   * @param pgResponseMessage the pgResponseMessage value.
   * @param pgName the pgName value.
   */
  public Payment(String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName) {
      this.paymentMethod =paymentMethod;
      this.pgPaymentId = pgPaymentId;
      this.pgStatus = pgStatus;
      this.pgResponseMessage = pgResponseMessage;
      this.pgName = pgName;
  }

}
