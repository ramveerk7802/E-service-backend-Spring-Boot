package com.rvcode.E_service.services;


import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllRegisteredUser(Role role){
        try {
            List<User> list = userRepository.findAllByRole(role);
            return list;

        }catch (Exception e){
            throw new MyCustomException("Error fetching the Registered user :-> "+ e.getMessage());
        }
    }
}
