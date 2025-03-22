package com.rvcode.E_service.services;


import com.rvcode.E_service.dtoObjects.NewServiceAddDto;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.ElectricianService;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.MyElectricianServiceRepository;
import com.rvcode.E_service.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MyElectricianService {

    private final MyElectricianServiceRepository myElectricianServiceRepository;
    private final UserRepository userRepository;


    public MyElectricianService(MyElectricianServiceRepository myElectricianServiceRepository, UserRepository userRepository) {
        this.myElectricianServiceRepository = myElectricianServiceRepository;
        this.userRepository = userRepository;
    }

    public List<ElectricianService> getAllServicesOfParticular(String email){
        try {
            Optional<User> optionalUser  = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not Authorized");
            return optionalUser.get().getElectrician().getServices();
        }catch (Exception e){
            throw  new MyCustomException("Error on getting All Service : ->"+e.getMessage());
        }
    }

    public ElectricianService save(NewServiceAddDto dto){
        try {
            Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not found that email");
            Electrician electrician = optionalUser.get().getElectrician();

            ElectricianService myService = new ElectricianService();

            myService.setServiceName(dto.getServiceName());
            myService.setBaseCharge(dto.getBaseCharge());
            myService.setDescription(dto.getDescription());
            myService.setElectrician(electrician);
            electrician.getServices().add(myService);

            ElectricianService saved = myElectricianServiceRepository.save(myService);
            return saved;
        }catch (Exception e){
            throw new MyCustomException("Error on Adding new Service by Electrician :-> "+e.getMessage());
        }
    }

    public boolean deleteById(Long id){
        try {
            myElectricianServiceRepository.deleteById(id);
            return true;

        }catch (Exception e){
            throw new MyCustomException("Error in deleting the service");
        }
    }


}
