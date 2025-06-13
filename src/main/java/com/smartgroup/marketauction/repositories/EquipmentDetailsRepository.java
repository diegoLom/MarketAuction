package com.smartgroup.marketauction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgroup.marketauction.entities.EquipmentDetails;

@Repository
public interface EquipmentDetailsRepository extends JpaRepository<EquipmentDetails, Long> {
   
}