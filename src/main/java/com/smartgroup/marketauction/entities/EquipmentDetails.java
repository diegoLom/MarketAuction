package com.smartgroup.marketauction.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipment_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corresponds to the item ID (e.g., 67352)

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost; 

    @Column(name = "retail_sale_count")
    private Integer retailSaleCount;

    @Column(name = "auction_sale_count")
    private Integer auctionSaleCount;

    @Column(name = "default_market_ratio", nullable = false)
    private Double defaultMarketRatio;

    @Column(name = "default_auction_ratio", nullable = false)
    private Double defaultAuctionRatio;

    @OneToMany(mappedBy = "equipmentDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<YearlyRatios> yearlyRatios = new ArrayList<>();

}