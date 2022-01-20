package com.technicalchallenge.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technicalchallenge.app.controller.CustumerRestController;
import com.technicalchallenge.app.exceptionscustom.ApiError;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.ICustomerDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.models.services.CustomerServiceImpl;
import com.technicalchallenge.app.models.services.ICustomerService;
import com.technicalchallenge.app.response.CustomersResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(CustumerRestController.class)
public class TestCustomerAll {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServiceImpl customerServiceImpl;

    @MockBean
    private CustumerRestController custumerRestController;

    @MockBean
    private IDocumentTypeDao documentTypeDao;

    @MockBean
    private ICountryDao countryDao;

    @MockBean
    private ICustomerDao customerDao;

    @TestConfiguration
    protected static class Config {
        @Bean
        public IDocumentTypeDao documentTypeDao() {
            return Mockito.mock(IDocumentTypeDao.class);
        }

        @Bean
        public CustomerServiceImpl customerServiceImpl() {
            return Mockito.mock(CustomerServiceImpl.class);
        }

        @Bean
        public ICustomerDao customerDao() {
            return Mockito.mock(ICustomerDao.class);
        }

        @Bean
        public ICountryDao countryDao() {
            return Mockito.mock(ICountryDao.class);
        }


    }


    @Test
    public void whenFindAll_thenReturnProductDTOList() throws Exception {
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
        List<Customers> productDTOs = Arrays.asList(customer);

        doReturn(new ArrayList<>()).when(customerServiceImpl).findAll();
        doReturn(productDTOs).when(custumerRestController).all();

        // when + then
        this.mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", is("jose")));
    }
}