package com.rvcode.E_service.controllers;


import com.rvcode.E_service.dtoObjects.UserOrElectricianRegistrationDto;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;



    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String heathCheck(){
        return "Health okk";
    }

    @PostMapping("/user-login")
    public String userLogin(){
        return "";
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> userRegister(@RequestBody UserOrElectricianRegistrationDto myDto){

        User saved = userService.registerForUserOrElectrician(myDto,Role.USER);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }



    @PostMapping("/electrician-register")
    public ResponseEntity<?> electricianRegister(@RequestBody UserOrElectricianRegistrationDto myDto){

       User saved = userService.registerForUserOrElectrician(myDto,Role.ELECTRICIAN);

        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

//    @GetMapping("/{myId}")
//    public User findById(@PathVariable Long myId){
//        return userService.findById(myId);
//    }


}
