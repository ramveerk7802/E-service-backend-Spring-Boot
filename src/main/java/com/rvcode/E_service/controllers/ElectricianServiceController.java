package com.rvcode.E_service.controllers;


import com.rvcode.E_service.dtoObjects.NewServiceAddDto;
import com.rvcode.E_service.entities.ElectricianService;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.services.MyElectricianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electrician-services")
public class ElectricianServiceController {

    private final MyElectricianService myElectricianService;

    public ElectricianServiceController(MyElectricianService myElectricianService) {
        this.myElectricianService = myElectricianService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllServices(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        String email = authentication.getName();
        List<ElectricianService> services = myElectricianService.getAllServicesOfParticular(email);
        return new ResponseEntity<>(services,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewService(@RequestBody NewServiceAddDto dto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        String email = authentication.getName();
        dto.setEmail(email);
        ElectricianService saved = myElectricianService.save(dto);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteServiceById(@RequestParam Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        boolean result = myElectricianService.deleteById(id);
        if(result)
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deletion");
    }
}
