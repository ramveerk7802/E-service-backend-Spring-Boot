package com.rvcode.E_service.services;

import com.rvcode.E_service.dtoObjects.CancelBookingResponseDto;
import com.rvcode.E_service.dtoObjects.UserOrElectricianRegistrationDto;
import com.rvcode.E_service.entities.BookingRequest;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.BookingStatus;
import com.rvcode.E_service.enums.Role;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.BookingRequestRepository;
import com.rvcode.E_service.repositories.ElectricianRepository;
import com.rvcode.E_service.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ElectricianRepository electricianRepository;
    private final BookingRequestRepository bookingRequestRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ElectricianRepository electricianRepository, BookingRequestRepository bookingRequestRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.electricianRepository = electricianRepository;
        this.bookingRequestRepository = bookingRequestRepository;
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
                    electricianRepository.deleteById(electrician.getId());
            }
            userRepository.deleteById(user.getId());
            return true;
        }catch (Exception e){
            throw new MyCustomException("Failed to Delete Account, please try again.");
        }
    }

    public Optional<User> findByEmail(String email){
        try {
            return userRepository.findByEmail(email);
        }catch (Exception e){
            throw new MyCustomException("Error on fetching data of Authenticated user/Current user :-> "+e.getMessage());
        }
    }

    public User updateAccount(String email){
        return null;
    }

    public CancelBookingResponseDto cancelBookingRequestIfNotConfirm(Long id){

        Optional<BookingRequest> optionalBookingRequest = bookingRequestRepository.findById(id);
        if(optionalBookingRequest.isEmpty()){
            throw new MyCustomException("Not Found Booking Request with id : "+id);
        }
        BookingRequest bookingRequest = optionalBookingRequest.get();
        if(bookingRequest.getBookingStatus()== BookingStatus.CONFIRMED){
            return new CancelBookingResponseDto(false,"Booking Already confirm by Electrician, so Not Cancel",bookingRequest);
        }
        bookingRequest.setBookingStatus(BookingStatus.CANCELLED);
        BookingRequest saved = bookingRequestRepository.save(bookingRequest);
        return new CancelBookingResponseDto(true,"Your Booking cancel successfully",saved);


    }




}
