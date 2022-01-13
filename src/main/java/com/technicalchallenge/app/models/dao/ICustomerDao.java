package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICustomerDao extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {
}
