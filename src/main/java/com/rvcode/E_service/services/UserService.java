package com.rvcode.E_service.services;

import com.rvcode.E_service.dtoObjects.UserOrElectricianRegistrationDto;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ElectricianService electricianService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ElectricianService electricianService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.electricianService = electricianService;
    }

    public User registerForUserOrElectrician(UserOrElectricianRegistrationDto userDto,Role role){
        try {
            if(userRepository.existsByEmail(userDto.getEmail())){
                throw new MyCustomException("User email already registered.");
            }
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setCity(userDto.getCity());
            user.setState(userDto.getState());
            user.setPinCode(userDto.getPinCode());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(Role.USER==role)
                user.setRole(Role.USER);
            else{
                user.setRole(Role.ELECTRICIAN);
                Electrician electrician = new Electrician();
                electrician.setUser(user);
                electrician.setAadhaarNumber(userDto.getAadhaarNumber());
                user.setElectrician(electrician);
            }
            User saveduser = userRepository.save(user);
            return saveduser;
        }catch (Exception e){
            throw new MyCustomException("Error on registration of user");
        }
    }


    public boolean deleteAccountByEmail(String email){
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("Failed to delete Account.");
            User user = optionalUser.get();
            if(user.getRole()==Role.ELECTRICIAN){
                Electrician electrician = user.getElectrician();
                if(electrician!=null)
                    electricianService.deleteById(electrician.getId());
            }
            userRepository.deleteById(user.getId());
            return true;
        }catch (Exception e){
            throw new MyCustomException("Failed to Delete Account, please try again.");
        }
    }

    public User findById(Long id){
        try{
            Optional<User> optionalUser = userRepository.findById(id);
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not Found with : "+id);
            return optionalUser.get();

        }catch (Exception e){
            throw new MyCustomException("Bad Request");
        }

    }


}
