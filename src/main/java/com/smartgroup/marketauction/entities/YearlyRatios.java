package com.smartgroup.marketauction.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "yearly_ratios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyRatios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for this table

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private EquipmentDetails equipmentDetails;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "market_ratio", nullable = false)
    private Double marketRatio;

    @Column(name = "auction_ratio", nullable = false)
    private Double auctionRatio;
}