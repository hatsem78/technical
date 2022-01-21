package com.technicalchallenge.app;

import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.ICustomerDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.exceptionscustom.CustomersUnder18Exception;
import com.technicalchallenge.app.models.services.CustomerServiceImpl;
import com.technicalchallenge.app.response.CustomersResponse;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.utils.ResponseCodes;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;

import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class TestCustomerServiceImpl {

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private ICustomerDao customerDao;

    @Mock
    private ICountryDao countryDao;

    @Mock
    private IDocumentTypeDao documentTypeDao;

    public UtilsTest utilsTest = new UtilsTest();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    private Optional<Customers> addCustomerOptionalTest() {
        Country country = new Country(1L, "Argentina", "Ag");
        countryDao.save(country);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        documentTypeDao.save(documentType);

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

        Optional<Customers> t = Optional.of(customer);

        return  t;
    }


    private CustomersBody addCustomerTest() {
        Country country = new Country(1L, "Argentina", "Ag");
        countryDao.save(country);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        documentTypeDao.save(documentType);

        CustomersBody customer = new CustomersBody(
                "Lopez",
                "Jose",
                "12313131321",
                "M",
                17,
                country.getId(),
                "Argentiana",
                documentType.getId()
        );

        return  customer;
    }

    private CustomersResponse addCustomersResponse() {
        Country country = new Country(1L,"Argentina", "Ag");
        countryDao.save(country);

        DocumentType documentType = new DocumentType(1L,"Documento de identidad", "dni");
        documentTypeDao.save(documentType);

        CustomersResponse customer = new CustomersResponse(
                1L,
                "Lopez",
                "Jose",
                "12313131321",
                "M",
                18,
                country,
                "Argentiana",
                documentType
        );

        return  customer;
    }

    private List<Customers> getCustomersList() {
        Country country = new Country(1L,"Argentina", "Ag");

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


        List<Customers> customers = new ArrayList<>();
        customers.add(customer);
        return customers;
    }

    @Test
    void testCustomSave() {
        // Given
        CustomersBody customer = utilsTest.getCustomersBody();

        when(customerService.save(any(CustomersBody.class))).then(new Answer<Customers>(){
            Long secuencia = 8L;
            @Override
            public Customers answer(InvocationOnMock invocation) throws Throwable {
                Customers customer = invocation.getArgument(0);
                customer.setId(secuencia++);
                return customer;
            }
        });


        // When
        Customers customer1 = customerService.save(customer);

        // Then
        assertTrue(customer1.getId() == customer.getId());
        verify(customerService).save(any(CustomersBody.class));
    }

    @Test
    void testCustomDelete() {

        // Given
        Customers customer = utilsTest.getAddCustomer();

        customer.setId(8L);

        customerDao.save(customer);

        CustomersBody customersBody = addCustomerTest();

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_FAIL,
                "Customer delete fail"
        );


        Mockito.when(customerService.delete(customer.getId())).thenReturn(
                new ResponseEntity<ResponseRequest>(response, HttpStatus.OK)
        );

        // When

        ResponseEntity<ResponseRequest> result = customerService.delete(customer.getId());

        // Then
        Assert.assertEquals(result.getBody().getMessage(), "Customer delete fail");
        verify(customerService).delete(customer.getId());
    }

    @Test
    void testCustomerfind() throws Exception {
        Customers customer = utilsTest.getAddCustomer();

        Mockito.when(customerService.find(customer.getId()))
                .thenReturn(customer);

        Customers result = customerService.find(customer.getId());

        Assert.assertEquals(customer.getId(), customer.getId());

        verify(customerService).find(customer.getId());

    }

    @Test
    void testCustomerfindAll() throws Exception {
        List<Customers> customersList = getCustomersList();

        customersList.get(0).setId(8L);

        when(customerService.findAll()).thenReturn(customersList);
        Mockito.when(customerService.find(customersList.get(0).getId()))
                .thenReturn(customersList.get(0));

        Customers resultCustomer = customerService.find(customersList.get(0).getId());

        List<Customers> result = customerService.findAll();



        assertEquals(1, result.size());
        assertTrue(resultCustomer.getLastName().contains("Lopez"));
        verify(customerService).findAll();
        verify(customerService).find(customersList.get(0).getId());

    }

    @Test
    @Tag("customer")
    @Tag("error")
    void testCustomerMustBeOver18YearsOld() {
        CustomersBody customer = utilsTest.getCustomersBody();
        Exception exception = assertThrows(CustomersUnder18Exception.class, () -> {
            customer.setEdad(5);
        });

        String actual = exception.getMessage();
        String esperado = "the client must be over 18 years old";
        assertEquals(esperado, actual);
    }
}