package com.technicalchallenge.app.models.services;


import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.response.CustomersResponse;

import java.util.List;

public interface ICustomerService {

    List<CustomersResponse> findAll();

    Customers save(Customers customers);

    CustomersResponse map(Customers customers);

}
