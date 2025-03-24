package com.rvcode.E_service.services;


import com.rvcode.E_service.dtoObjects.ServiceTypeDto;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.ElectricianRepository;
import com.rvcode.E_service.repositories.ServiceTypeRepository;
import com.rvcode.E_service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ElectricianService {

    private final ElectricianRepository electricianRepository;
    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public ElectricianService(ElectricianRepository electricianRepository, UserRepository userRepository, ServiceTypeRepository serviceTypeRepository) {
        this.electricianRepository = electricianRepository;
        this.userRepository = userRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }


    public List<ServiceType> getAllServicesOfParticular(String email){
        try {
            Optional<User> optionalUser  = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not Authorized");
            Electrician electrician =  optionalUser.get().getElectrician();
           return electrician.getServicesTypeList();
        }catch (Exception e){
            throw  new MyCustomException("Error on getting All Service : ->"+e.getMessage());
        }
    }

    public ServiceType addNewServiceType(String email,ServiceTypeDto dto){
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not authorized");
            User user = optionalUser.get();
            Electrician electrician = user.getElectrician();

            boolean serviceExist = electrician.getServicesTypeList()
                    .stream()
                    .anyMatch(existingService-> existingService.getServiceName().equalsIgnoreCase(dto.getServiceName()));

            if(serviceExist)
                throw new MyCustomException("Service type already exists for this electrician.");

            ServiceType serviceType = new ServiceType();
            serviceType.setServiceName(dto.getServiceName());
            serviceType.setBaseCharge(dto.getBaseCharge());
            serviceType.setDescription(dto.getDescription());


            //  Save ServiceType first
            ServiceType savedService = serviceTypeRepository.save(serviceType);

            //  Add saved service to the electrician's list & update electrician
            electrician.getServicesTypeList().add(savedService);
            electricianRepository.save(electrician);

            return savedService;
        }catch (Exception e){
            throw new MyCustomException("Error on adding New Service type"+e.getMessage());
        }
    }




}
