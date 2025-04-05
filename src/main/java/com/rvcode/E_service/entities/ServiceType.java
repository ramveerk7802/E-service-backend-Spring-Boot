package com.rvcode.E_service.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "service_types")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;  // Example: "Fan Repair", "AC Repair", etc.

    @Column(nullable = false)
    private Double baseCharge;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    @JsonIgnore
    private Electrician electrician;

}
