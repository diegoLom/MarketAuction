package com.smartgroup.marketauction.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.smartgroup.marketauction.dto.CostCalculationResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CostCalculationApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testApiWithGivenParameters() {
        ResponseEntity<CostCalculationResult> response1 = restTemplate.getForEntity(
                "/cost?id=67352&year=2007", CostCalculationResult.class);
        System.out.println("Result for Year 2007, ID 67352: " + response1.getBody());

        ResponseEntity<String> response2 = restTemplate.getForEntity(
                "/cost?id=87964&year=2011", String.class);
        System.out.println("Result for Year 2011, ID 87964: " + response2.getBody());
    }
}