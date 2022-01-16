package com.technicalchallenge.app.controller;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.exceptionscustom.EntityNotFoundException;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.models.services.CustomerServiceImpl;
import com.technicalchallenge.app.response.CustomersResponse;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
    private ICountryDao countryDao;

    @Autowired
    private IDocumentTypeDao documentTypeDao;

    @GetMapping(value = {"/index", "/", "/home"})
    public String index(){
        return "Welcome to the technical challenge";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customers>> all() {

        List<Customers> responseEntity = customerService.findAll();

        if(responseEntity.isEmpty()) {
            ResponseRequest documentResponse = new ResponseRequest(
                    400,
                    "Not found any customers"
            );
            ResponseEntity responseEntitys = new ResponseEntity<>(documentResponse, HttpStatus.BAD_REQUEST);

            return responseEntitys;
        }

        return new ResponseEntity<List<Customers>>(responseEntity, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseRequest> add(@RequestBody @Valid CustomersBody customer, BindingResult errors) throws EntityNotFoundException {

        logger.info("AdminUser Controller - create - Begin ;");

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer create ok"
        );

        Optional<Country> country = countryDao.findById(customer.getCountry());

        Optional<DocumentType> documentType = documentTypeDao.findById(customer.getDocument_type());

        if (country.isEmpty()) {
            response = new ResponseRequest(
                    ResponseCodes.COUNTRY_NOT_FOUND,
                    "Country not found"
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } else if(documentType.isEmpty()){
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

        customerSave.setCountry(country.get());

        Customers result = null;
        result = customerService.save(customerSave);

        if (result == null) {
            response = new ResponseRequest(
                    ResponseCodes.CUSTOMER_DELETE_FAIL,
                    "Customer delete fail"
            );
        }

        logger.info("AdminUser Controller - create - End ;");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "find/{customerId}")
    public ResponseEntity<Customers>  find (
            @PathVariable("customerId") int customerId
    ) throws EntityNotFoundException {
        logger.info("Customer find id {}", customerId);

        Customers responseEntity = customerService.find((long) customerId);

        if(responseEntity == null) {
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
    public ResponseEntity<ResponseRequest>  delete (
            @PathVariable("customerId") int customerId
    ) throws EntityNotFoundException {

        logger.info("Customer delete id {}", customerId);

        return customerService.delete((long) customerId);
    }
}
