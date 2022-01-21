package com.technicalchallenge.app.RequestBody;

import com.sun.istack.NotNull;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;

public class CustomersRelationshipBody {

    @NotNull
    private Long id;
    @NotNull
    private Long customerParent;
    @NotNull
    private Long customerRelation;
    @NotNull
    private Long typeRelationship;

    public CustomersRelationshipBody(Long id, Long customerParent, Long customerRelation, Long typeRelationship) {
        this.id = id;
        this.customerParent = customerParent;
        this.customerRelation = customerRelation;
        this.typeRelationship = typeRelationship;
    }

    public CustomersRelationshipBody() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerParent() {
        return customerParent;
    }

    public void setCustomerParent(Long customerParents) {
        this.customerParent = customerParents;
    }

    public Long getCustomerRelation() {
        return customerRelation;
    }

    public void setCustomerRelation(Long customerRelation) {
        if (!customerRelation.equals(this.customerParent)) {
            this.customerRelation = customerRelation;
        } else {
            throw new CustomersCustomException("customerRelation one must not be equal to CustomerParent");
        }
    }

    public Long getTypeRelationship() {
        return typeRelationship;
    }

    public void setTypeRelationship(Long typeRelationship) {
        this.typeRelationship = typeRelationship;
    }
}
