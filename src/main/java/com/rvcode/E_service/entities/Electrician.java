package com.rvcode.E_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
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

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id",unique = true)
    private User user;

    @OneToMany(mappedBy = "electrician",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ElectricianService> services = new ArrayList<>();

}
