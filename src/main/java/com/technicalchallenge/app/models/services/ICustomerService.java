package com.technicalchallenge.app.models.services;


import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.response.CustomersResponse;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.response.StatisticsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customers> findAll();

    Customers save(Customers customers);

    Customers update(CustomersBody customers);

    ResponseEntity<ResponseRequest> delete(long customerId);

    Customers find(Long customers);

    StatisticsResponse getStadistic();

}
