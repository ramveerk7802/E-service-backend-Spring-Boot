package com.rvcode.E_service.dtoObjects;


import lombok.Data;

@Data
public class NewServiceAddDto {

    private String email;
    private String serviceName;
    private double baseCharge;
    private String description;
}
