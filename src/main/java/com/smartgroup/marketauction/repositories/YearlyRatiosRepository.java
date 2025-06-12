package com.smartgroup.marketauction.repositories;

import com.smartgroup.marketauction.entities.YearlyRatios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YearlyRatiosRepository extends JpaRepository<YearlyRatios, Long> {
    Optional<YearlyRatios> findByEquipmentDetailsIdAndYearRatio(Long equipmentId, Integer yearRatio);
}