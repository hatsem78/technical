package com.technicalchallenge.app;

import com.technicalchallenge.app.controller.CustumerRestController;
import com.technicalchallenge.app.exceptionscustom.ApiError;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCustomRestExceptionHandler {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @LocalServerPort
    private int port;

    @Before
    public void setup() {

        RestAssured.port = port;
    }

    @Test
    public void GetPriceBitcoinEthereumTimestamp() {
        Response response = RestAssured.when().get("/api/add");
        ApiError error = response.as(ApiError.class);

        assertTrue(HttpStatus.NOT_FOUND == error.getStatus());
        assertEquals("Not Found", HttpStatus.NOT_FOUND.value(), response.statusCode());
    }

}
