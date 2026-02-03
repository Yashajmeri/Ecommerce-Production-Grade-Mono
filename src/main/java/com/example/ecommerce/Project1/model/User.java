package com.example.ecommerce.Project1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    private  Long userId;
    @NotBlank
    @Size(max = 50)
    @Column(name="username")
    private String username;
    @NotBlank
    @Size(min = 8,max = 120)
    @Column(name="password")
    private String password;
    @Email
    @Column(name="email")
    private String email;
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name="user_Id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
private Set<Role> roles = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
//    @JoinTable(name="user_addresses",
//    joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns = @JoinColumn(name="addressId"))
    private List<Address> addresses = new ArrayList<>();
    @ToString.Exclude
    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
   private Cart cart;
    @ToString.Exclude
    @OneToMany(mappedBy="user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private  Set<Product> products;

}
