package com.rvcode.E_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "electricians")
public class Electrician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String aadhaarNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    @OneToMany(mappedBy = "electrician", cascade = CascadeType.ALL)
    private List<BookingRequest> bookingRequests = new ArrayList<>();

    @OneToMany(mappedBy = "electrician", cascade = CascadeType.ALL)
    private List<ServiceType> offeredServices = new ArrayList<>();
}
