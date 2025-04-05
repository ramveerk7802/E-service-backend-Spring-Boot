package com.rvcode.E_service.controllers;


import com.rvcode.E_service.dtoObjects.UserOrElectricianRegistrationDto;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.services.AuthServices;
import com.rvcode.E_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;


    @GetMapping("/health-check")
    public String heathCheck(){
        return "Health okk";
    }



    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Map<String,String> request){
        String email = request.get("email");
        String password = request.get("password");
        User user = authServices.authenticateUser(email,password);
        if(user!=null){
            return new ResponseEntity<>("Login successfully.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Please check the entered detail for log in",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> userRegister(@RequestBody UserOrElectricianRegistrationDto myDto){

        User saved = authServices.registerForUserOrElectrician(myDto,Role.CUSTOMER);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }



    @PostMapping("/electrician-register")
    public ResponseEntity<?> electricianRegister(@RequestBody UserOrElectricianRegistrationDto myDto){

       User saved = authServices.registerForUserOrElectrician(myDto,Role.ELECTRICIAN);

        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }




}
