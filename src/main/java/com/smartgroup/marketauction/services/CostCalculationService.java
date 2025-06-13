package com.smartgroup.marketauction.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartgroup.marketauction.dto.CostCalculationResult;
import com.smartgroup.marketauction.entities.EquipmentDetails;
import com.smartgroup.marketauction.entities.YearlyRatios;
import com.smartgroup.marketauction.repositories.EquipmentDetailsRepository;
import com.smartgroup.marketauction.repositories.YearlyRatiosRepository;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ExerciseNotFoundException;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ModelIdNotFoundException;

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

        BigDecimal marketValue;
        BigDecimal auctionValue;
        BigDecimal cost = equipment.getCost();

        if (yearly != null) {
            marketValue = cost.multiply(BigDecimal.valueOf(yearly.getMarketRatio())).setScale(5, RoundingMode.HALF_UP);
            auctionValue = cost.multiply(BigDecimal.valueOf(yearly.getAuctionRatio())).setScale(5, RoundingMode.HALF_UP);
        } else {

          //  List<YearlyRatios> yearlyRatios =  yearlyRatiosRepository.findByOrderByYearRatioAsc();
            Optional<YearlyRatios> oLastYearly = yearlyRatiosRepository.findFirstByOrderByYearRatioAsc();
	        YearlyRatios  lastYearly = oLastYearly.orElseThrow(() -> new ExerciseNotFoundException("No yearly ratios found for model ID: " + modelId));
        
            BigDecimal marketValueLastYear  = cost.multiply(BigDecimal.valueOf(lastYearly.getMarketRatio()));
            BigDecimal auctionValueLastYear  = cost.multiply(BigDecimal.valueOf(lastYearly.getAuctionRatio()));
            
            EquipmentDetails lastYearlyEquipment = lastYearly.getEquipmentDetails();

            for(int x = year; x < lastYearly.getYearRatio() ; x++) {
                marketValueLastYear  = marketValueLastYear.multiply(new BigDecimal(1).subtract(new BigDecimal(lastYearlyEquipment.getDefaultMarketRatio())));
                auctionValueLastYear  = auctionValueLastYear.multiply(new BigDecimal(1).subtract(new BigDecimal(lastYearlyEquipment.getDefaultAuctionRatio())));
            }

            marketValue = marketValueLastYear.setScale(5, RoundingMode.HALF_UP);
            auctionValue = auctionValueLastYear.setScale(5, RoundingMode.HALF_UP);
        }

        return new CostCalculationResult(marketValue, auctionValue);
    }
}