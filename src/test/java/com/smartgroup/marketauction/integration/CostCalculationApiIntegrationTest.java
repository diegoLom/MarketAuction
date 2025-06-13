package com.smartgroup.marketauction.integration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.smartgroup.marketauction.dto.CostCalculationResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CostCalculationApiIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(CostCalculationApiIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testApiWithGivenParameters() {
        ResponseEntity<CostCalculationResult> response1 = restTemplate.getForEntity(
                "/cost?id=67352&year=2006", CostCalculationResult.class);
        logger.info("Result for Year 2006, ID 67352: {}", response1.getBody());

        ResponseEntity<String> response2 = restTemplate.getForEntity(
                "/cost?id=67352&year=2005", String.class);
        logger.info("Result for Year 2005, ID 67352: {}", response2.getBody());
    }
}