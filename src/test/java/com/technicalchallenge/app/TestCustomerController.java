package com.technicalchallenge.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technicalchallenge.app.RequestBody.CustomersBody;
import com.technicalchallenge.app.controller.CustumerRestController;
import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.ICustomerDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.models.services.CustomerServiceImpl;
import com.technicalchallenge.app.response.ResponseRequest;
import com.technicalchallenge.app.utils.ResponseCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CustumerRestController.class)
public class TestCustomerController {

    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

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


    public UtilsTest utilsTest = new UtilsTest();

    @TestConfiguration
    protected static class Config {
        @Bean
        public IDocumentTypeDao documentTypeDao() {
            return Mockito.mock(IDocumentTypeDao.class);
        }

        @Bean
        public UtilsTest utilsTest() {

            return new UtilsTest();
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
    public void whenFindAll_thenReturnCustomerList() throws Exception {
        List<Customers> customerDTOs = utilsTest.getCustomersList();

        doReturn(customerDTOs).when(customerServiceImpl).findAll();
        doReturn(new ResponseEntity<List<Customers>>(customerDTOs, HttpStatus.OK)).when(custumerRestController).all();

        // when + then
        this.mockMvc.perform(get("/api/customer/all/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", is("Jose")));
    }

    @Test
    public void whenFind_thenReturnCustomer() throws Exception {

        List<Customers> customerDTOs = utilsTest.getCustomersList();


        doReturn(customerDTOs.get(0)).when(customerServiceImpl).find(customerDTOs.get(0).getId());
        doReturn(new ResponseEntity<Customers>(customerDTOs.get(0), HttpStatus.OK))
                .when(custumerRestController).find(customerDTOs.get(0).getId());

        this.mockMvc.perform(get("/api/customer/find/" + customerDTOs.get(0).getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("Jose")));
    }


    @Test
    public void whenFind_thenReturnCustomerDelete() throws Exception {

        List<Customers> customerDTOs = utilsTest.getCustomersList();

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer delete ok"
        );

        doReturn(new ResponseEntity<ResponseRequest>(response, HttpStatus.OK))
                .when(customerServiceImpl).delete(customerDTOs.get(0).getId());

        doReturn(new ResponseEntity<ResponseRequest>(response, HttpStatus.OK))
                .when(custumerRestController).delete(customerDTOs.get(0).getId());

        this.mockMvc.perform(delete("/api/customer/delete/" + customerDTOs.get(0).getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Customer delete ok")));
    }

    @Test
    public void whenFind_thenReturnCustomerDeleteNotFound() throws Exception {

        List<Customers> customerDTOs = utilsTest.getCustomersList();

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer delete ok"
        );

        doReturn(new ResponseEntity<ResponseRequest>(response, HttpStatus.OK))
                .when(customerServiceImpl).delete(2333);

        doReturn(new ResponseEntity<ResponseRequest>(response, HttpStatus.OK))
                .when(custumerRestController).delete(233);

        this.mockMvc.perform(delete("/api/customer/delete/" + 233))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Customer delete ok")));
    }

    @Test
    public void createProduct() throws Exception {
        CustomersBody customerDTOs = utilsTest.getCustomersBody();
        List<Customers> customer = utilsTest.getCustomersList();

        ResponseRequest response = new ResponseRequest(
                ResponseCodes.CUSTOMER_DELETE_OK,
                "Customer delete ok"
        );

        doReturn(customer.get(0))
                .when(customerServiceImpl).save(customerDTOs);

        doReturn(new ResponseEntity<ResponseRequest>(response, HttpStatus.OK))
                .when(custumerRestController).add(utilsTest.getCustomersBody());

        String json = "{\n" +
                "\t\"lastName\": \"Lopezc\",\n" +
                "    \"name\": \"octavio\",\n" +
                "    \"documentNumber\": \"1321321\",\n" +
                "    \"gender\": \"x\",\n" +
                "    \"edad\": 18,\n" +
                "    \"country\": 1,\n" +
                "\t\"nationality\": \"argentina\",\n" +
                "\t\"document_type\": 1,\n" +
                "    \"contact\": [\n" +
                "            {\n" +
                "                \"name\": \"algo\",\n" +
                "                \"email\": null,\n" +
                "                \"phone\": null,\n" +
                "                \"id\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"algo 2\",\n" +
                "                \"email\": null,\n" +
                "                \"phone\": null,\n" +
                "                \"id\": 2\n" +
                "            }\n" +
                "        ]\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/customer/add/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is created successfully");
    }
}