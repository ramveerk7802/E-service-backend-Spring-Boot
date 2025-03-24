package com.rvcode.E_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;  // Reference to predefined service types

//    @Column(nullable = false)
//    private double baseCharge; // Initial charge for the requested service

//    @Column(length = 255)
//    private String additionalDescription; // Customer can add extra notes

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Links to the customer who requested the service

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    private Electrician electrician; // Assigned electrician (nullable initially)

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalServiceCharge> additionalServiceChargeList = new ArrayList<>();
}
