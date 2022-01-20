package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.CustomersRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ICustomersRelationshipDao extends JpaRepository<CustomersRelationship, Long>, JpaSpecificationExecutor<CustomersRelationship> {
    List<CustomersRelationship> findCustomersRelationshipByCustomerParentOrCustomerRelation(Customers customerParent, Customers customerRelation);

    CustomersRelationship findCustomersRelationshipByCustomerParentAndCustomerRelation(Customers customerParent, Customers customerRelation);
}
