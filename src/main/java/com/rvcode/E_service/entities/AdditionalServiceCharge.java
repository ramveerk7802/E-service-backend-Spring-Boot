package com.rvcode.E_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "additional_service_charges")
public class AdditionalServiceCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_request_id", nullable = false)
    @JsonIgnore
    private BookingRequest bookingRequest;  // Links additional charges to an existing request

    @Column(nullable = false)
    private String additionalServiceName; // Description like "Lamp Repair"

    @Column(nullable = false)
    private double charge; // Extra charge added
}
