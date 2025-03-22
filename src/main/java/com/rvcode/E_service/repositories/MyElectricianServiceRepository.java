package com.rvcode.E_service.repositories;

import com.rvcode.E_service.entities.ElectricianService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyElectricianServiceRepository extends JpaRepository<ElectricianService,Long> {
}
