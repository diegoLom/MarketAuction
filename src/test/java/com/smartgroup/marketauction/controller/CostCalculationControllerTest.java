package com.smartgroup.marketauction.controller;

import com.smartgroup.marketauction.dto.CostCalculationResult;
import com.smartgroup.marketauction.services.CostCalculationService;
import com.smartgroup.marketauction.web.controller.CostCalculationController;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ExerciseNotFoundException;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ModelIdNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CostCalculationController.class)
class CostCalculationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CostCalculationService costCalculationService;

        @Test
        void whenValidIdAndYear_thenReturnsCostCalculation() throws Exception {
                CostCalculationResult result = new CostCalculationResult(1200.0, 800.0);

                Mockito.when(costCalculationService.calculateCostValues(1L, 2022)).thenReturn(result);

                mockMvc.perform(get("/cost")
                                .param("id", "1")
                                .param("year", "2022")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.marketValue").value(1200.0))
                                .andExpect(jsonPath("$.auctionValue").value(800.0));
        }

        @Test
        void whenIdNotFound_thenReturns404() throws Exception {
                Mockito.when(costCalculationService.calculateCostValues(anyLong(), anyInt()))
                                .thenThrow(new ModelIdNotFoundException("Model ID not found"));

                mockMvc.perform(get("/cost")
                                .param("id", "999")
                                .param("year", "2022")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        void whenYearNotFound_thenReturns404() throws Exception {
                Mockito.when(costCalculationService.calculateCostValues(anyLong(), anyInt()))
                                .thenThrow(new ExerciseNotFoundException("Year not found"));

                mockMvc.perform(get("/cost")
                                .param("id", "1")
                                .param("year", "9999")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        void whenIdIsEmpty_thenReturns400() throws Exception {
                mockMvc.perform(get("/cost")
                                .param("id", "")
                                .param("year", "2022")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void whenIdIsNonNumeric_thenReturns400() throws Exception {
                mockMvc.perform(get("/cost")
                                .param("id", "abc")
                                .param("year", "2022")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void whenYearIsEmpty_thenReturns400() throws Exception {
                mockMvc.perform(get("/cost")
                                .param("id", "1")
                                .param("year", "")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void whenYearIsNonNumeric_thenReturns400() throws Exception {
                mockMvc.perform(get("/cost")
                                .param("id", "1")
                                .param("year", "abc")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }
}