package com.technicalchallenge.app.controller;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.RequestBody.CustomersRelationshipBody;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.exceptionscustom.EntityNotFoundException;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.ICustomersRelationshipDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.dao.ITypeRelationshipDao;
import com.technicalchallenge.app.models.entity.*;
import com.technicalchallenge.app.models.services.CustomerServiceImpl;
import com.technicalchallenge.app.models.services.CustomersRelationshipServiceImpl;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.response.StatisticsResponse;
import com.technicalchallenge.app.utils.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/customer")
public class CustumerRestController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ICustomersRelationshipDao customersRelationshipDao;

    @Autowired
    private ITypeRelationshipDao typeRelationshipDao;

    @Autowired
    private CustomersRelationshipServiceImpl customersRelationshipService;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDocumentTypeDao documentTypeDao;

    @GetMapping(value = {"/index", "/", "/home"})
    public String index() {
        return "Welcome to the technical challenge";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customers>> all() {

        List<Customers> responseEntity = customerService.findAll();

        if (responseEntity.isEmpty()) {
            ResponseRequest documentResponse = new ResponseRequest(
                    400,
                    "Not found any customers"
            );
            ResponseEntity responseEntitys = new ResponseEntity<>(documentResponse, HttpStatus.BAD_REQUEST);

            return responseEntitys;
        }

        return new ResponseEntity<List<Customers>>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/add")
    public ResponseEntity<ResponseRequest> add(
            @RequestBody @Valid CustomersBody customer
    ) throws EntityNotFoundException {

        log.info("Customer Controller - create - Begin ;");

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_CREATION_FAIL,
                "Customer create ok"
        );

        Customers result = customerService.save(customer);
        if (result == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_CREATION_FAIL,
                    "Customer Create fail"
            );
        }

        log.info("Customer Controller - create - End ;");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update/{customerId}")
    public ResponseEntity<ResponseRequest> update(
            @RequestBody @Valid CustomersBody customer,
            @PathVariable("customerId") long customerId,
            BindingResult errors
    ) throws EntityNotFoundException {

        log.info("Customer Controller - update - Begin ;");

        try {
            Customers tutorialData = customerService.find(customerId);
        } catch (Exception ex) {
            throw new CustomersCustomException("Customers not exist");
        }

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_UPDATE_OK,
                "Customer update ok"
        );

        Customers result = customerService.update(customer);

        if (result == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_CREATION_FAIL,
                    "Customer delete fail"
            );
        }

        log.info("Customer Controller - create - End ;");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "find/{customerId}")
    public ResponseEntity<Customers> find(
            @PathVariable("customerId") Long customerId
    ) throws EntityNotFoundException {
        log.info("Customer find id {}", customerId);

        Customers responseEntity = customerService.find(customerId);

        if (responseEntity == null) {
            ResponseRequest documentResponse = new ResponseRequest(
                    400,
                    "The client does not exist"
            );
            ResponseEntity responseEntitys = new ResponseEntity<ResponseRequest>(documentResponse, HttpStatus.BAD_REQUEST);

            return responseEntitys;
        }

        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{customerId}")
    public ResponseEntity<ResponseRequest> delete(
            @PathVariable("customerId") long customerId
    ) throws EntityNotFoundException {

        log.info("Customer delete id {}", customerId);

        return customerService.delete(customerId);
    }

    @GetMapping("/stadistic")
    public ResponseEntity<StatisticsResponse> estadisticas() {

        StatisticsResponse responseEntity = customerService.getStadistic();

        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }


    @PostMapping("/persons")
    public ResponseEntity<ResponseRequest> personsRelationship(
            @RequestBody @Valid CustomersRelationshipBody customer
    ) throws EntityNotFoundException {

        log.info("Customer Controller - create  relationship - Begin ;");

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_CREATION_FAIL,
                "Customer relationship create ok"
        );

        Customers customerParents = customerService.find(customer.getCustomerParent());
        TypeRelationship relationShips = typeRelationshipDao.getById(customer.getTypeRelationship());
        Customers customerRelations = customerService.find(customer.getCustomerRelation());

        if (customerParents == null || customerRelations == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_NOT_EXIST,
                    "Customer not exist"
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        CustomersRelationship customersRelationship = new CustomersRelationship(
                customerParents,
                customerRelations,
                relationShips
        );

        customersRelationshipService.save(customersRelationship);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "relationship/{customerParent}/{customerRelation}")
    public ResponseEntity<HashMap<String, String>> getRelationship(
            @PathVariable("customerParent") Long customerParent,
            @PathVariable("customerRelation") Long customerRelation
    ) throws EntityNotFoundException {
        log.info("Customer getRelationship id {}; : {};", customerParent, customerRelation);

        Customers customerParents = customerService.find(customerParent);
        Customers customerRelations = customerService.find(customerRelation);

        if (customerParents == null) {
            throw new CustomersCustomException("CustomerParents does not exist");
        }

        if (customerRelations == null) {
            throw new CustomersCustomException("customerRelations does not exist");
        }

        CustomersRelationship responseEntityRelationship =
                customersRelationshipDao.findCustomersRelationshipByCustomerParentAndCustomerRelation(
                        customerParents,
                        customerRelations
                );

        if (customerRelations == null) {
           throw new CustomersCustomException("Relations does not exist");
        }

        HashMap<String, String> entityRelationship = new HashMap<String, String>();

        entityRelationship.put("Response", responseEntityRelationship.toString());

        return new ResponseEntity<>(entityRelationship, HttpStatus.OK);
    }

}
