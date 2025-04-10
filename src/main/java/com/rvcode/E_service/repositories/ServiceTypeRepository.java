package com.rvcode.E_service.repositories;

import com.rvcode.E_service.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType,Long> {
    Optional<ServiceType> findByServiceName(String serviceName);
}
