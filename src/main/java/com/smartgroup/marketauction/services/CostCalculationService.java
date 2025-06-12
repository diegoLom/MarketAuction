package com.smartgroup.marketauction.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.smartgroup.marketauction.dto.CostCalculationResult;
import com.smartgroup.marketauction.entities.EquipmentDetails;
import com.smartgroup.marketauction.entities.YearlyRatios;
import com.smartgroup.marketauction.repositories.EquipmentDetailsRepository;
import com.smartgroup.marketauction.repositories.YearlyRatiosRepository;
import com.smartgroup.marketauction.web.errorhandling.ModelIdNotFoundException;

@Service
public class CostCalculationService {

    private final EquipmentDetailsRepository equipmentDetailsRepository;
    private final YearlyRatiosRepository yearlyRatiosRepository;

    public CostCalculationService(EquipmentDetailsRepository equipmentDetailsRepository,
                                  YearlyRatiosRepository yearlyRatiosRepository) {
        this.equipmentDetailsRepository = equipmentDetailsRepository;
        this.yearlyRatiosRepository = yearlyRatiosRepository;
    }

    public CostCalculationResult calculateCostValues(Long modelId, Integer year) {
        EquipmentDetails equipment = equipmentDetailsRepository.findById(modelId)
                .orElseThrow(() -> new ModelIdNotFoundException("Model ID not found: " + modelId));

     
        YearlyRatios yearly = yearlyRatiosRepository
                .findByEquipmentDetailsIdAndYearRatio(modelId, year)
                .orElse(null);

        double marketValue;
        double auctionValue;
        BigDecimal cost = equipment.getCost();

        if (yearly != null) {
            marketValue = cost.multiply(BigDecimal.valueOf(yearly.getMarketRatio())).doubleValue();
            auctionValue = cost.multiply(BigDecimal.valueOf(yearly.getAuctionRatio())).doubleValue();
        } else {
            // Use default ratios from EquipmentDetails
            marketValue = cost.multiply(BigDecimal.valueOf(equipment.getDefaultMarketRatio())).doubleValue();
            auctionValue = cost.multiply(BigDecimal.valueOf(equipment.getDefaultAuctionRatio())).doubleValue();
        }

        return new CostCalculationResult(marketValue, auctionValue);
    }
}