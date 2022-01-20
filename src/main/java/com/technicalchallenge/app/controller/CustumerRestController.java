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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/api/customer")
public class CustumerRestController {

    private static final Logger logger = LoggerFactory.getLogger(CustumerRestController.class);

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
            @RequestBody @Valid CustomersBody customer,
            BindingResult errors
    ) throws EntityNotFoundException {

        logger.info("Customer Controller - create - Begin ;");

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_CREATION_FAIL,
                "Customer create ok"
        );

        if (customer.getContact() == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_CONTACT_HAVE_ONE_CONTACT,
                    "Customers must have a contact"
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Country> country = countryDao.findCountryByCountryId(customer.getCountry());

        Optional<DocumentType> documentType = documentTypeDao.findById(customer.getDocument_type());

        if (country.isEmpty()) {
            response = new ResponseRequest(
                    ResponseCodes.COUNTRY_NOT_FOUND,
                    "Country not found"
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } else if (documentType.isEmpty()) {
            response = new ResponseRequest(
                    ResponseCodes.DOCUMENT_TYPE_NOT_FOUND,
                    "Document Type not found"
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Customers customerSave = new Customers(
                customer.getLastName(),
                customer.getName(),
                customer.getDocumentNumber(),
                customer.getGender(),
                customer.getEdad(),
                country.get(),
                customer.getNationality(),
                documentType.get()
        );

        customerSave.setDocument_type(documentType.get());

        customerSave.setContact(customer.getContact());

        customerSave.setCountry(country.get());

        Customers result = null;
        result = customerService.save(customerSave);

        if (result == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_CREATION_FAIL,
                    "Customer Create fail"
            );
        }

        logger.info("Customer Controller - create - End ;");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update/{customerId}")
    public ResponseEntity<ResponseRequest> update(
            @RequestBody @Valid CustomersBody customer,
            @PathVariable("customerId") long customerId,
            BindingResult errors
    ) throws EntityNotFoundException {

        logger.info("Customer Controller - update - Begin ;");

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

        logger.info("Customer Controller - create - End ;");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "find/{customerId}")
    public ResponseEntity<Customers> find(
            @PathVariable("customerId") int customerId
    ) throws EntityNotFoundException {
        logger.info("Customer find id {}", customerId);

        Customers responseEntity = customerService.find((long) customerId);

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

        logger.info("Customer delete id {}", customerId);

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

        logger.info("Customer Controller - create  relationship - Begin ;");

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
        logger.info("Customer getRelationship id {}; : {};", customerParent, customerRelation);

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
