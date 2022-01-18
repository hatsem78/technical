package com.technicalchallenge.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import com.technicalchallenge.app.exceptionscustom.CustomersUnder18Exception;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Entity
@Table(
        name = "customers",
        indexes = {
                @Index(
                        name = "unique_person",
                        columnList = "document_type,documentNumber,country,gender",
                        unique = true
                )
        }
)
public class Customers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacts> contact;


    @NotNull
    private String nationality;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type", nullable = false)
    private DocumentType document_type;


    public Customers() {

    }

    public Customers(
            String lastName,
            String name,
            String documentNumber,
            String gender,
            int edad,
            Country country,
            String nationality,
            DocumentType document_type
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

    public Customers(
            String lastName,
            String name,
            String documentNumber,
            String gender,
            int edad,
            Country country,
            String nationality,
            DocumentType document_type,
            List<Contacts> contact

    ) {
        this.lastName = lastName;
        this.name = name;
        this.documentNumber = documentNumber;
        this.gender = gender;
        this.edad = edad;
        this.country = country;
        this.nationality = nationality;
        this.document_type = document_type;
        this.contact = contact;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
        this.edad = edad;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public DocumentType getDocument_type() {
        return document_type;
    }

    public void setDocument_type(DocumentType document_type) {
        this.document_type = document_type;
    }

    public List<Contacts> getContact() {
        return contact;
    }

    public void setContact(List<Contacts> contact) {
        this.contact = contact;
    }
}
