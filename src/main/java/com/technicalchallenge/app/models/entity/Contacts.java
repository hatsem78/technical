package com.technicalchallenge.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="contacts")
public class Contacts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contact_id;

    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_id"), name = "customer_id")
    @JsonIgnore
    private Customers customers;

    public Long getId() {
        return contact_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
