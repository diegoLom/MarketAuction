package com.smartgroup.marketauction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgroup.marketauction.dto.CostCalculationResult;
import com.smartgroup.marketauction.services.CostCalculationService;

@RestController
@RequestMapping("/cost")
public class CostCalculationController {

    private final CostCalculationService costCalculationService;

    public CostCalculationController(CostCalculationService costCalculationService) {
        this.costCalculationService = costCalculationService;
    }

    @GetMapping
    public ResponseEntity<CostCalculationResult> getCostCalculation(
            @RequestParam Long id,
            @RequestParam Integer year) {
        CostCalculationResult result = costCalculationService.calculateCostValues(id, year);
        return ResponseEntity.ok(result);
    }
}