package com.rvcode.E_service.repositories;

import com.rvcode.E_service.entities.Electrician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ElectricianRepository extends JpaRepository<Electrician,Long> {
}
