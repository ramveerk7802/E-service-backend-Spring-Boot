package com.rvcode.E_service.entities;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Data
//@Table(name = "electrician_services")
//public class ElectricianService {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "electrician_id", nullable = false)
//    private User electrician; // The electrician providing the service
//
//    @ManyToOne
//    @JoinColumn(name = "service_type_id", nullable = false)
//    private ServiceType serviceType; // The type of service (Fan Repair, AC Repair, etc.)
//
//    @Column(nullable = false)
//    private Double baseCharge; // Minimum price for the service
//}


@Entity
@Data
@Table(name = "electrician_services")
public class ElectricianService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "electrician_id", nullable = false)
    private Electrician electrician;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private Double baseCharge;

    @Column(length = 255)
    private String description;
}
