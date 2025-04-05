package com.rvcode.E_service.services;


import com.rvcode.E_service.entities.BookingRequest;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.BookingStatus;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.repositories.BookingRequestRepository;
import com.rvcode.E_service.repositories.ServiceTypeRepository;
import com.rvcode.E_service.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingRequestService {

    private final BookingRequestRepository bookingRequestRepository;
    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;


    public BookingRequestService(BookingRequestRepository bookingRequestRepository, UserRepository userRepository, ServiceTypeRepository serviceTypeRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
        this.userRepository = userRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public BookingRequest addNewServiceRequest(String email, Long id){
        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new MyCustomException("User not Found"));

            ServiceType serviceType = serviceTypeRepository.findById(id).orElseThrow(()-> new MyCustomException("Service Not Found with id: "+id));

            Electrician electrician = serviceType.getElectrician();
            if(electrician==null)
                throw new MyCustomException("Electrician data is null");
            BookingRequest bookingRequest = new BookingRequest();


            bookingRequest.setServiceType(serviceType);
            bookingRequest.setElectrician(electrician);
            bookingRequest.setUser(user);
            bookingRequest.setBookingStatus(BookingStatus.PENDING);

            BookingRequest r =  bookingRequestRepository.save(bookingRequest);
            return r;

        }catch (Exception e){
            throw new MyCustomException("Error while adding a new service request.");
        }
    }
}
