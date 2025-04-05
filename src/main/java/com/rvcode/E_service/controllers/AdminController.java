package com.rvcode.E_service.controllers;


import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.repositories.UserRepository;
import com.rvcode.E_service.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/customer")
    public ResponseEntity<?> getAllRegisteredCustomer(){
        List<User> customerList = adminServices.findAllRegisteredUser(Role.CUSTOMER);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }
    @GetMapping("/electrician")
    public ResponseEntity<?> getAllRegisteredElectrician(){
        List<User> list = adminServices.findAllRegisteredUser(Role.ELECTRICIAN);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
