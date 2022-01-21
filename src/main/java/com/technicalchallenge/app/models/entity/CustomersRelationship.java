package com.technicalchallenge.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="CustomersRelationship")
public class CustomersRelationship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "parent_id"), name = "parent_id")
    @JsonIgnore
    private Customers customerParent;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_relation", nullable = false)
    private Customers customerRelation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "relation_id", nullable = false)
    private TypeRelationship typeRelationship;

    public CustomersRelationship(
            Customers customerParent,
            Customers customerRelation,
            TypeRelationship typeRelationship
    ) {
        this.customerParent = customerParent;
        this.customerRelation = customerRelation;
        this.typeRelationship = typeRelationship;
    }

    public CustomersRelationship() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customers getCustomerParent() {
        return customerParent;
    }

    public void setCustomerParent(Customers customerParent) {
        this.customerParent = customerParent;
    }

    public Customers getCustomerRelation() {
        return customerRelation;
    }

    public void setCustomerRelation(Customers customerRelation) {
        this.customerRelation = customerRelation;
    }

    public TypeRelationship getTypeRelationship() {
        return typeRelationship;
    }

    public void setTypeRelationship(TypeRelationship typeRelationship) {
        this.typeRelationship = typeRelationship;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Parentesco: ");
        sb.append(this.getCustomerParent().getLastName() + " " + this.getCustomerParent().getName())
                .append(" tiene una relación de: ")
                .append(this.typeRelationship.getName())
                .append(" con: ")
                .append(this.customerRelation.getLastName() + " " + this.customerRelation.getName());

        return sb.toString();
    }
}
