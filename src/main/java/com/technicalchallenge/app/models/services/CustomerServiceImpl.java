package com.technicalchallenge.app.models.services;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.exceptionscustom.CustomersUnder18Exception;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.ICustomerDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.response.CustomersResponse;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDocumentTypeDao documentTypeDao;

    @Override
    public List<Customers> findAll() {

        List<CustomersResponse> customersResponseList = new ArrayList<>();
        List<Customers> customersList = customerDao.findAll();

        logger.info("CustomerServiceImpl - Find All Customers - Result ;{};", customersResponseList.size());
        return customersList;
    }

    @Override
    public Customers save(Customers customers) {
        Customers response = null;

        logger.info("AuthRole - Create User - Init ;{};{};",
                customers.getLastName(), customers.getName()
        );

        ResponseRequest responseRequest;


        /*Customers customer = new Customers(
                customers.getLastName(),
                customers.getName(),
                customers.getDocumentNumber(),
                customers.getGender(),
                customers.getEdad(),
                country.get(),
                customers.getNationality(),
                documentType.get()
        );*/

        //customer.setCreateAt(new Date());

        response = customerDao.save(customers);



        logger.info(" Create Customer - Result ;{};{};",
                ResponseCodes.CUSTOMER_CREATION_OK,
                "Customer creation ok");
        return response;

    }

    @Override
    public ResponseEntity<ResponseRequest> delete(long customerId) {

        logger.info("Customer - Delete - Init ;{};{};", customerId);

        ResponseRequest response = null;

        try {
            customerDao.deleteById(customerId);
        } catch (Exception e) {
            logger.error("Customer delete - Exception {}", e.getMessage());
            throw new CustomersCustomException("Customer delete - Exception not exist customer id: " + customerId);
        }

        response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer delete ok"
        );

        logger.info("Customer - Delete - Init ;{}; code {};", customerId, ResponseCodes.CUSTOMER_DELETE_OK);

        return new ResponseEntity<ResponseRequest>(response, HttpStatus.OK);
    }



    @Override
    public Customers find(Long customers) {
        return customerDao.findById(customers).get();
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
