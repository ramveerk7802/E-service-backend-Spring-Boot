package com.rvcode.E_service.dtoObjects;

import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private String name;
    private String email;
    private String phone;
    private Role role;
    private String city;
    private String state;
    private String pinCode;
    private Electrician electrician;
}
