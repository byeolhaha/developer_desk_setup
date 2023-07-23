package com.example.developerdesk.equipment.repository;

import com.example.developerdesk.equipment.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
