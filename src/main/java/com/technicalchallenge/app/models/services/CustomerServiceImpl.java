package com.technicalchallenge.app.models.services;

import com.technicalchallenge.app.models.dao.ICustomerDao;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.response.CustomersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public List<CustomersResponse> findAll() {

        List<CustomersResponse> customersResponseList = new ArrayList<>();
        List<Customers> customersList = customerDao.findAll();

        CustomersResponse response = null;

        for (Customers customer : customersList) {
            try {
                response = map(customer);
                customersResponseList.add(response);
            } catch (Exception e) {
                logger.error("CustomerServiceImpl Service - Error mapping");
            }
        }

        logger.info("CustomerServiceImpl - Find All Customers - Result ;{};", customersResponseList.size());
        return customersResponseList;
    }


    @Override
    public Customers save(Customers customers) {
        return customerDao.save(customers);
    }

    public CustomersResponse map(Customers customers) {
        CustomersResponse response = new CustomersResponse(
                customers.getId(),
                customers.getLastName(),
                customers.getName(),
                customers.getDocumentNumber(),
                customers.getGender(),
                customers.getEdad(),
                customers.getCountry(),
                customers.getNationality(),
                customers.getDocument_type()
        );

        return response;
    }

}
