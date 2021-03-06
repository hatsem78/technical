package com.technicalchallenge.app.models.services;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.models.dao.*;
import com.technicalchallenge.app.models.entity.*;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.response.StatisticsResponse;
import com.technicalchallenge.app.utils.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDocumentTypeDao documentTypeDao;

    @Autowired
    private IContactDao contactDao;

    @Autowired
    private ICustomersRelationshipDao customersRelationshipDao;

    @Override
    public List<Customers> findAll() {

        List<Customers> customersList = customerDao.findAll();

        log.info("CustomerServiceImpl - Find All Customers - Result ;{};", customersList.size());
        return customersList;
    }

    @Override
    public Customers save(CustomersBody customer) {

        if (customer.getContact() == null) {
            throw new CustomersCustomException("Customers must have a contact id: " + customer.getContact());
        }

        Optional<Country> country = countryDao.findCountryByCountryId(customer.getCountry());

        Optional<DocumentType> documentType = documentTypeDao.findById(customer.getDocument_type());

        if (country.isEmpty()) {
            throw new CustomersCustomException("Country not found id: " + country);
        } else if (documentType.isEmpty()) {
            throw new CustomersCustomException("Document Type not found id:" + country);
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

        Customers result = customerDao.save(customerSave);

        for (Contacts contact : result.getContact()) {
            contact.setCustomers(result);
            contactDao.save(contact);
        }

        log.info(" Create Customer - Result ;{};{};",
                ResponseCodes.CUSTOMER_CREATION_OK,
                "Customer creation ok");
        return result;
    }

    @Override
    public Customers update(CustomersBody customer) {

        log.info("Update User - Init ;{};{};",
                customer.getLastName(), customer.getName()
        );

        if (customer.getContact() == null) {
            throw new CustomersCustomException("Customers must have a contact");
        }

        Optional<Country> country = countryDao.findById(customer.getCountry());

        Optional<DocumentType> documentType = documentTypeDao.findById(customer.getDocument_type());

        if (country.isEmpty()) {
            throw new CustomersCustomException("Customers must have a contact");
        } else if (documentType.isEmpty()) {
            throw new CustomersCustomException("Customers must have a contact");
        }

        Customers customerSave = new Customers(
                customer.getLastName(),
                customer.getName(),
                customer.getDocumentNumber(),
                customer.getGender(),
                customer.getEdad(),
                country.get(),
                customer.getNationality(),
                documentType.get(),
                customer.getContact()
        );

        customerSave.setId(customer.getId());

        customerSave.setDocument_type(documentType.get());

        customerSave.setCountry(country.get());

        Customers response = customerDao.save(customerSave);

        log.info(" Update Customer - Result ;{};{};",
                ResponseCodes.CUSTOMER_CREATION_OK,
                "Customer creation ok");

        for (Contacts contact : response.getContact()) {
            contact.setCustomers(customerSave);
            contactDao.save(contact);
        }
        return response;
    }

    @Override
    public ResponseEntity<ResponseRequest> delete(long customerId) {

        log.info("Customer - Delete - Init ;{};{};", customerId);

        ResponseRequest response = null;

        Customers customerCcontact = customerDao.getById(customerId);

        List<Contacts> deleteContact = contactDao.findAllByCustomers(customerCcontact);

        List<CustomersRelationship> customersRelationship =
                customersRelationshipDao.findCustomersRelationshipByCustomerParentOrCustomerRelation(customerCcontact, customerCcontact);

        if (deleteContact != null) {
            log.error("Customer-Delete - all contact");
            for (Contacts contact : deleteContact) {
                contactDao.deleteById(contact.getContact_id());
            }
        }

        if (customersRelationship != null) {
            log.error("Customer-Delete - all contact");
            for (CustomersRelationship relationship : customersRelationship) {
                customersRelationshipDao.deleteById(relationship.getId());
            }
        }

        try {
            customerDao.deleteById(customerId);
        } catch (Exception e) {
            log.error("Customer delete - Exception {}", e.getMessage());
            throw new CustomersCustomException("Customer-Delete - no exist customer id: " + customerId);
        }


        response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer delete ok"
        );

        log.info("Customer - Delete - Init ;{}; code {};", customerId, ResponseCodes.CUSTOMER_DELETE_OK);

        return new ResponseEntity<ResponseRequest>(response, HttpStatus.OK);
    }

    @Override
    public Customers find(Long customers) {
        Customers response = null;
        try {
            response = customerDao.findById(customers).get();
        } catch (Exception e) {
            log.error("Customer find - Exception {}", e.getMessage());
            throw new CustomersCustomException("Customer-Find - no exist customer id:: " + customers);
        }
        return response;
    }

    @Override
    public StatisticsResponse getStadistic() {
        return customerDao.getStadistic();
    }

}
