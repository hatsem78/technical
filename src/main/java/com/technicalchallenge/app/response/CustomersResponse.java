package com.technicalchallenge.app.response;

import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.DocumentType;

import javax.validation.constraints.NotEmpty;

public class CustomersResponse {

    @NotEmpty
    private Long Id;
    @NotEmpty
    private String LastName;
    @NotEmpty
    private String name;
    @NotEmpty
    private String documentNumber;
    @NotEmpty
    private String gender;
    @NotEmpty
    private int edad;

    @NotEmpty
    private Country country;

    @NotEmpty
    private String nationality;
    @NotEmpty
    private DocumentType documentType;

    public CustomersResponse(){

    }

    public CustomersResponse(
            Integer code,
            String message,
            Long id,
            String lastName,
            String name,
            String documentNumber,
            String gender,
            int edad,
            Country country,
            String nationality,
            DocumentType documentType
    ) {
        Id = id;
        LastName = lastName;
        this.name = name;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.edad = edad;
        this.country = country;
        this.nationality = nationality;
        this.documentType = documentType;
    }

    public CustomersResponse(
            Long id, String lastName, String name,
            String documentNumber, String gender,
            int edad, Country country,
            String nationality, DocumentType documentType
    ) {
        Id = id;
        LastName = lastName;
        this.name = name;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.edad = edad;
        this.country = country;
        this.nationality = nationality;
        this.documentType = documentType;
    }
}

