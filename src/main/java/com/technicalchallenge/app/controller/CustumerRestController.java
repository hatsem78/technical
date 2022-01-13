package com.technicalchallenge.app.controller;

import com.technicalchallenge.app.models.services.ICustomerService;
import com.technicalchallenge.app.response.CustomersResponse;
import com.technicalchallenge.app.response.ResponseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustumerRestController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping(value = {"/index", "/", "/home"})
    public String index(){
        return "Welcome to the technical challenge";
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomersResponse>> all() {

        List<CustomersResponse> responseEntity = customerService.findAll();

        List<CustomersResponse> operationImageResponseList = new ArrayList<>();
        if(responseEntity == null) {
            ResponseRequest documentResponse = new ResponseRequest(
                    400,
                    "No se encontraron personas"
            );
            ResponseEntity responseEntitys = new ResponseEntity<ResponseRequest>(documentResponse, HttpStatus.BAD_REQUEST);

            return responseEntitys;
        }

        operationImageResponseList = (List<CustomersResponse>) responseEntity;

        return new ResponseEntity<List<CustomersResponse>>(operationImageResponseList, HttpStatus.OK);
    }

}
