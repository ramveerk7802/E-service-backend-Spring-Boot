package com.rvcode.E_service.services;


import com.rvcode.E_service.dtoObjects.ServiceTypeDto;
import com.rvcode.E_service.entities.BookingRequest;
import com.rvcode.E_service.entities.Electrician;
import com.rvcode.E_service.entities.ServiceType;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.enums.BookingStatus;
import com.rvcode.E_service.exception.MyCustomException;
import com.rvcode.E_service.exception.UserNotAuthorized;
import com.rvcode.E_service.repositories.BookingRequestRepository;
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
    private final BookingRequestRepository bookingRequestRepository;

    public ElectricianService(ElectricianRepository electricianRepository, UserRepository userRepository, ServiceTypeRepository serviceTypeRepository, BookingRequestRepository bookingRequestRepository) {
        this.electricianRepository = electricianRepository;
        this.userRepository = userRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.bookingRequestRepository = bookingRequestRepository;
    }


    public List<ServiceType> getAllServicesOfParticular(String email){
        try {
            Optional<User> optionalUser  = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("User not Authorized");
            Electrician electrician =  optionalUser.get().getElectrician();
           return electrician.getOfferedServices();
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

            boolean serviceExist = electrician.getOfferedServices()
                    .stream()
                    .anyMatch(existingService-> existingService.getServiceName().equalsIgnoreCase(dto.getServiceName()));

            if(serviceExist)
                throw new MyCustomException("Service type already exists for this electrician.");

            ServiceType serviceType = new ServiceType();
            serviceType.setServiceName(dto.getServiceName());
            serviceType.setBaseCharge(dto.getBaseCharge());
            serviceType.setDescription(dto.getDescription());
            serviceType.setElectrician(user.getElectrician());



            //  Save ServiceType first
            ServiceType savedService = serviceTypeRepository.save(serviceType);

            //  Add saved service to the electrician's list & update electrician
            electrician.getOfferedServices().add(savedService);
            electricianRepository.save(electrician);

            return savedService;
        }catch (Exception e){
            throw new MyCustomException("Error on adding New Service type"+e.getMessage());
        }
    }

    public Boolean deleteServiceTypeById(String email, Long id) {
        try {
            // Fetch user by email
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new MyCustomException("Electrician not found with email -> " + email);
            }

            // Get the electrician
            Electrician electrician = optionalUser.get().getElectrician();
            if (electrician == null) {
                throw new MyCustomException("No electrician profile found for user.");
            }

            // Check if the service type exists and belongs to the electrician
            Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(id);
            if (optionalServiceType.isEmpty()) {
                throw new MyCustomException("Service type with ID " + id + " not found.");
            }

            ServiceType serviceType = optionalServiceType.get();
            if (!electrician.getOfferedServices().contains(serviceType)) {
                throw new MyCustomException("Service type does not belong to the authenticated electrician.");
            }

            // Remove the service type from the electrician's list
            electrician.getOfferedServices().remove(serviceType);
            electricianRepository.save(electrician);

            // Delete the service type from the repository
            serviceTypeRepository.delete(serviceType);

            return true;
        } catch (Exception e) {
            log.error("Failed to delete Service type :-> "+e.getMessage());
            return false;
//            throw new MyCustomException("Error while deleting ServiceType: " + e.getMessage());
        }
    }


    public ServiceType updateServiceTypeById(String email,Long id,ServiceTypeDto dto){
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new MyCustomException("Electrician not found with email -> " + email);
            }

            Electrician electrician = optionalUser.get().getElectrician();

            if(electrician==null){
                throw new MyCustomException("No electrician profile found for user.");
            }


            Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(id);
            if (optionalServiceType.isEmpty()) {
//                throw new UserNotAuthorized("ServiceType  not exist with id :"+ id);
                throw new MyCustomException("Service type with ID " + id + " not found.");
            }

            ServiceType serviceType = optionalServiceType.get();

            if (!electrician.getOfferedServices().contains(serviceType)) {
                throw new MyCustomException("Service type does not belong to the authenticated electrician.");
            }

            if(dto.getServiceName()!=null && !dto.getServiceName().isEmpty()){
                boolean serviceTypeExist  = electrician.getOfferedServices()
                        .stream()
                        .anyMatch(currentServiceType-> currentServiceType.getServiceName().equalsIgnoreCase(dto.getServiceName()) && !currentServiceType.getId().equals(id));
                if(serviceTypeExist){
                    throw new MyCustomException("Service name already exists for this electrician.");
                }
                serviceType.setServiceName(dto.getServiceName());
            }


            if(dto.getBaseCharge()>0){
                serviceType.setBaseCharge(dto.getBaseCharge());
            }

            if(dto.getDescription()!=null && !dto.getDescription().isEmpty())
                serviceType.setDescription(dto.getDescription());

            ServiceType updatedServiceType = serviceTypeRepository.save(serviceType);
            return updatedServiceType;

        }catch (Exception e){
            throw new MyCustomException("Failed to Update the Service Type"+e.getMessage());
        }
    }


    public List<BookingRequest> getAllBookingRequest(String email){
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isEmpty())
                throw new MyCustomException("Error : User not Found");
            User user = optionalUser.get();
            Electrician electrician = user.getElectrician();
            if(electrician==null)
                throw new MyCustomException("This User is not electrician");
            List<BookingRequest> list = electrician.getBookingRequests();
            return list;
        }catch (Exception e){
            log.error("Error getting All Booking Request -> "+ e.getMessage());
            return null;
        }

    }


    public BookingRequest updateBookingStatusById(Long id, String bookingStatus) {
        try {
            Optional<BookingRequest> optionalBookingRequest = bookingRequestRepository.findById(id);
            if(optionalBookingRequest.isEmpty())
                throw new MyCustomException("Not Found Booking Request with id : "+id);
            BookingRequest myBookingRequest = optionalBookingRequest.get();

            if(bookingStatus.equalsIgnoreCase(BookingStatus.APPROVED.name()))
                myBookingRequest.setBookingStatus(BookingStatus.APPROVED);
            else if(bookingStatus.equalsIgnoreCase(BookingStatus.REJECT.name()))
                myBookingRequest.setBookingStatus(BookingStatus.REJECT);
            BookingRequest saved = bookingRequestRepository.save(myBookingRequest);
            return saved;
        }catch (Exception e){
            log.error("Error on Booking status update");
            return null;
        }
    }
}
