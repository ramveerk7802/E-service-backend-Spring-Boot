package com.rvcode.E_service.dtoObjects;

import lombok.Data;

@Data
public class UserOrElectricianRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String state;
    private String pinCode;
}
