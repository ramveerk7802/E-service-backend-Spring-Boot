package com.rvcode.E_service.utility;

import org.springframework.stereotype.Component;

@Component
public class Validator {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public boolean emailValidator(String email){
        if(email == null || email.isBlank())
            return false;
        return email.matches(emailRegex);
    }

    public boolean phoneNumberValidator(String phone){
        if(phone==null || phone.isBlank())
            return false;
        String regex = "^[0-9]{10}$";
        return phone.matches(regex);
    }
    public boolean pinCodeValidator(String pinCode){
        if(pinCode==null || pinCode.isBlank())
            return false;
        String regex = "^[0-9]{6}$";
        return pinCode.matches(regex);
    }
    public boolean aadhaarNumberValidator(String uidai){
        if(uidai==null || uidai.isBlank())
            return false;
        String regex = "^[0-9]{12}$";
        return uidai.matches(regex);
    }
}
