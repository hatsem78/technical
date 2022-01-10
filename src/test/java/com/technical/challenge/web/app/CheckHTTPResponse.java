package com.technical.challenge.web.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckHTTPResponse {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate TestRestTemplate;


    @Test
    public void shouldPassIfStringMatches() {
        assertEquals("Welcome to the technical challenge",
                TestRestTemplate.getForObject("http://localhost:" + port + "/", String.class)
        );
    }


}
