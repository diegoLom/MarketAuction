package com.smartgroup.marketauction.services;

import com.smartgroup.marketauction.entities.EquipmentDetails;
import com.smartgroup.marketauction.entities.YearlyRatios;
import com.smartgroup.marketauction.repositories.EquipmentDetailsRepository;
import com.smartgroup.marketauction.repositories.YearlyRatiosRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CostCalculationServiceTest {

   @Mock
    private EquipmentDetailsRepository equipmentDetailsRepository;

    @Mock
    private YearlyRatiosRepository yearlyRatiosRepository;

    @InjectMocks
    private CostCalculationService costCalculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateCostValues_WithYearlyRatios() {
        Long modelId = 1L;
        Integer year = 2022;

        EquipmentDetails equipment = Instancio.of(EquipmentDetails.class)
                .set(Select.field("id"), modelId)
                .set(Select.field("cost"), new BigDecimal("1000.00"))
                .set(Select.field("defaultMarketRatio"), 1.1)
                .set(Select.field("defaultAuctionRatio"), 0.9)
                .create();

        YearlyRatios yearly = Instancio.of(YearlyRatios.class)
                .set(Select.field("year"), year)
                .set(Select.field("marketRatio"), 1.2)
                .set(Select.field("auctionRatio"), 0.8)
                .create();

        when(equipmentDetailsRepository.findById(modelId)).thenReturn(Optional.of(equipment));
        when(yearlyRatiosRepository.findByYear(year)).thenReturn(List.of(yearly));

        CostCalculationResult result = costCalculationService.calculateCostValues(modelId, year);

        assertNotNull(result);
        assertEquals(1200.00, result.getMarketValue());
        assertEquals(800.00, result.getAuctionValue());
    }

    @Test
    void testCalculateCostValues_WithoutYearlyRatios_UsesDefaults() {
        Long modelId = 2L;
        Integer year = 2023;

        EquipmentDetails equipment = Instancio.of(EquipmentDetails.class)
                .set(Select.field("id"), modelId)
                .set(Select.field("cost"), new BigDecimal("2000.00"))
                .set(Select.field("defaultMarketRatio"), 1.05)
                .set(Select.field("defaultAuctionRatio"), 0.95)
                .create();

        when(equipmentDetailsRepository.findById(modelId)).thenReturn(Optional.of(equipment));
        when(yearlyRatiosRepository.findByYear(year)).thenReturn(List.of());

        CostCalculationResult result = costCalculationService.calculateCostValues(modelId, year);

        assertNotNull(result);
        assertEquals(2100.00, result.getMarketValue());
        assertEquals(1900.00, result.getAuctionValue());
    }

    @Test
    void testCalculateCostValues_ModelIdNotFound_ThrowsException() {
        Long modelId = 999L;
        Integer year = 2022;

        when(equipmentDetailsRepository.findById(modelId)).thenReturn(Optional.empty());

        assertThrows(ModelIdNotFoundException.class, () ->
                costCalculationService.calculateCostValues(modelId, year));
    }

    @Test
    void testCalculateCostValues_YearNotFound_ThrowsException() {
        Long modelId = 1L;
        Integer year = 9999;

        EquipmentDetails equipment = Instancio.of(EquipmentDetails.class)
                .set(Select.field("id"), modelId)
                .create();

        when(equipmentDetailsRepository.findById(modelId)).thenReturn(Optional.of(equipment));
        when(yearlyRatiosRepository.findByYear(year)).thenReturn(null); 

        assertThrows(ExerciseNotFoundException.class, () ->
                costCalculationService.calculateCostValues(modelId, year));
    } 
}