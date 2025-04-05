package com.rvcode.E_service.dtoObjects;

import lombok.Data;

@Data
public class BookingRequestDto {
    private Long userId;
    private Long serviceTypeId;
}