package com.rvcode.E_service.services;

import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.ElectricianRepository;
import org.springframework.stereotype.Service;

@Service
public class ElectricianService {

    private final ElectricianRepository electricianRepository;

    public ElectricianService(ElectricianRepository electricianRepository) {
        this.electricianRepository = electricianRepository;
    }

    public Electrician save(Electrician electrician){
        try {

            Electrician saved = electricianRepository.save(electrician);
            return saved;
        }catch (Exception e){
            throw new MyCustomException("Error on registered as electrician");
        }
    }
}
