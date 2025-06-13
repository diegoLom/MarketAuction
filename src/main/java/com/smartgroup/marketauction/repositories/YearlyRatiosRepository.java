package com.smartgroup.marketauction.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgroup.marketauction.entities.YearlyRatios;

@Repository
public interface YearlyRatiosRepository extends JpaRepository<YearlyRatios, Long> {
    Optional<YearlyRatios> findByEquipmentDetailsIdAndYearRatio(Long equipmentId, Integer yearRatio);
}