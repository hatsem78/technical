package com.technicalchallenge.app;

import com.technicalchallenge.app.controller.CustumerRestController;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.models.services.ICustomerService;
import com.technicalchallenge.app.response.CustomersResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustumerRestController.class)
public class TestCustomerAdd {

    @Autowired
    ICustomerService customerServiceImpl;

    @Autowired
    private CustumerRestController custumerRestController;

    @Mock
    private IDocumentTypeDao documentTypeDao;

    @Mock
    private ICountryDao countryDao;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
    }

    @TestConfiguration
    protected static class Config {
        @Bean
        public IDocumentTypeDao documentTypeDao() {
            return Mockito.mock(IDocumentTypeDao.class);
        }

        @Bean
        public ICountryDao countryDao() {
            return Mockito.mock(ICountryDao.class);
        }

        @Bean
        public ICustomerService customerServiceImpl() {
            return Mockito.mock(ICustomerService.class);
        }
    }

    @Test
    public void getCustomerAllReturnNotFound() throws Exception {
        // When
        final ResultActions result = mockMvc.perform(
                post("/api/customer/add/")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
        result.andExpect(status().isOk());
        result.andExpect(content().json("[]"));
    }

    @Test
    public void getAllCustomers() {
        getCustomersList();
        ResponseEntity<List<Customers>> page = custumerRestController.all();
        assertTrue(page.getBody().size() == 1);
    }

    /*private void initGetAllUsersRules() {
        List<CustomersResponse> page = initPage();
        when(customerServiceImpl.findAll()).thenReturn((List<CustomersResponse>) page);
    }*/

    private List<CustomersResponse> initPage() {
        List<CustomersResponse> customersResponseList = new ArrayList<>();

        CustomersResponse response = new CustomersResponse();

        for (Customers customer : getCustomersList()) {
            try {
                when(customerServiceImpl.map(customer)).thenReturn(response);
                customersResponseList.add(response);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        return customersResponseList;
    }

    private List<Customers> getCustomersList() {
        Country country = new Country("Argentina", "Ag");

        DocumentType documentType = new DocumentType("Documento de identidad", "dni");

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
}