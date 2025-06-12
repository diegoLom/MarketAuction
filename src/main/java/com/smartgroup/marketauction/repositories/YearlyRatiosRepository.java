package com.smartgroup.marketauction.repositories;

import com.smartgroup.marketauction.entities.YearlyRatios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyRatiosRepository extends JpaRepository<YearlyRatios, Long> {
    List<YearlyRatios> findByYear(Integer year);
}