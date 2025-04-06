package com.rvcode.E_service.dtoObjects;

import com.rvcode.E_service.entities.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CancelBookingResponseDto {
    private Boolean status;
    private String message;
    private BookingRequest bookingRequest;
}
