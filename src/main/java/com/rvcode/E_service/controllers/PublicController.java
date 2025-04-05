package com.rvcode.E_service.controllers;


import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.services.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private PublicService publicService;


    @GetMapping
    public ResponseEntity<?> getAllServicesProvidedByAllElectrician(){
        List<ServiceType> list = publicService.getAllServiceTypeProvidedByAllElectrician();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
