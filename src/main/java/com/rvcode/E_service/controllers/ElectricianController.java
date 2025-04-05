package com.rvcode.E_service.controllers;


import com.rvcode.E_service.dtoObjects.ServiceTypeDto;
import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.services.ElectricianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electrician-services")
public class ElectricianController {

    private final ElectricianService electricianService;

    public ElectricianController(ElectricianService electricianService) {
        this.electricianService = electricianService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllServices(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        String email = authentication.getName();
        List<ServiceType> services = electricianService.getAllServicesOfParticular(email);
        return new ResponseEntity<>(services,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewService(@RequestBody ServiceTypeDto dto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        String email = authentication.getName();

        ServiceType saved = electricianService.addNewServiceType(email,dto);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteServiceTypeById(@RequestParam Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        boolean result = electricianService.deleteServiceTypeById(authentication.getName(),id);
        if(result)
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deletion");
    }

    @PutMapping
    public ResponseEntity<?> updateById(@RequestParam Long id,@RequestBody ServiceTypeDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        ServiceType updated = electricianService.updateServiceTypeById(authentication.getName(), id,dto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }
}
