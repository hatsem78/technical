package com.technicalchallenge.app.RequestBody;

import com.sun.istack.NotNull;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.exceptionscustom.CustomersUnder18Exception;
import com.technicalchallenge.app.models.entity.Contacts;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CustomersBody {
    @NotNull
    private long id;

    @NotNull
    private String lastName;

    @NotNull
    private String name;

    @NotNull
    private String documentNumber;

    @NotNull
    private String gender;

    @NotNull
    private int edad;

    @NotNull
    private Long country;

    @NotNull
    private String nationality;

    @NotNull
    private Long document_type;

    private List<Contacts> contact;

    public CustomersBody(){}

    public CustomersBody(
            String lastName,
            String name,
            String documentNumber,
            String gender,
            int edad,
            Long country,
            String nationality,
            Long document_type
    ) {
        this.lastName = lastName;
        this.name = name;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.edad = edad;
        this.country = country;
        this.nationality = nationality;
        this.document_type = document_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 18){
            throw new CustomersUnder18Exception("the client must be over 18 years old");
        }
        this.edad = edad;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getDocument_type() {
        return document_type;
    }

    public void setDocument_type(Long document_type) {
        this.document_type = document_type;
    }

    public List<Contacts> getContact() {
        return contact;
    }

    public void setContact(List<Contacts> contact) {
        if (contact.size() == 0){
            throw new CustomersCustomException("Customers must have a contact");
        }
        this.contact = contact;
    }
}

