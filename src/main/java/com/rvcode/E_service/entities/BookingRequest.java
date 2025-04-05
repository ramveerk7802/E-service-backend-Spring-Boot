package com.rvcode.E_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rvcode.E_service.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "service_requests")
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;  // Reference to predefined service types

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user; // Links to the customer who requested the service

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    @JsonIgnore
    private Electrician electrician;

    @OneToMany(mappedBy = "bookingRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalServiceCharge> additionalServiceChargeList = new ArrayList<>();
}
