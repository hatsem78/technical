package com.technicalchallenge.app;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Contacts;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UtilsTest {

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDocumentTypeDao documentTypeDao;

    public UtilsTest() {
    }

    public List<Customers> getCustomersList( Customers ... customerAdd) {

        Country country = new Country(1L,"Argentina", "Ag");
        country.setId(1L);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        Customers customer = new Customers(
                "Lopez",
                "Jose",
                "12313131321",
                "M",
                18,
                country,
                "Argentiana",
                documentType
        );

        Contacts contact = new Contacts();
        contact.setContact_id(1L);
        contact.setName("Juan el lindo");

        List<Contacts> contacts = Arrays.asList(contact);

        customer.setId(1L);
        customer.setContact((List<Contacts>) contacts);

        List<Customers> customerDTOs = new ArrayList<>();
        customerDTOs.add(customer);

        if(customerAdd.length > 0) {
            customerDTOs.add(customerAdd[0]);
        }
        return customerDTOs;
    }

    public CustomersBody getCustomersBody(Customers ... customerAdd) {

        Country country = new Country(1L,"Argentina", "Ag");
        country.setId(1L);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        CustomersBody customer = new CustomersBody(
                "Lopez",
                "Jose",
                "12313131321",
                "M",
                18,
                1L,
                "Argentiana",
                1L
        );

        Contacts contact = new Contacts();
        contact.setContact_id(1L);
        contact.setName("Juan el lindo");
        List<Contacts> contactList = Arrays.asList(contact);
        customer.setContact(contactList);


        return customer;
    }


    public Customers getAddCustomer() {
        Country country = new Country(1L,"Argentina", "Ag");
        countryDao.save(country);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        documentTypeDao.save(documentType);

        Customers customer = new Customers(
                "Lopez",
                "Jose",
                "12313131321",
                "M",
                17,
                country,
                "Argentiana",
                documentType
        );

        return  customer;
    }
}
