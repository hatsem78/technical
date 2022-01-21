package com.technicalchallenge.app.models.services;

import com.technicalchallenge.app.models.entity.CustomersRelationship;
import com.technicalchallenge.app.response.ResponseRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomersRelationship {

    List<CustomersRelationship> findAll();

    CustomersRelationship save(CustomersRelationship customersRelationship);

    CustomersRelationship update(CustomersRelationship customers);

    ResponseEntity<ResponseRequest> delete(long customersRelationship);

    CustomersRelationship find(Long customers);
}
