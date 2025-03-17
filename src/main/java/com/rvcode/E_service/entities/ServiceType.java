package com.rvcode.E_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "service_types")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serviceName; // e.g., "Fan Repair", "AC Repair"
}
