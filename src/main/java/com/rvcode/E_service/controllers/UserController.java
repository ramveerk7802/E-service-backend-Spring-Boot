package com.rvcode.E_service.controllers;


import com.rvcode.E_service.dtoObjects.UserResponseDto;
import com.rvcode.E_service.entities.User;
import com.rvcode.E_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<?> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("User not Authorized");
        }
        String email = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(email);
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        User user = optionalUser.get();

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();


        userService.deleteAccountByEmail(email);
        return ResponseEntity.ok("Account deleted successfully");
    }

//    @PutMapping
//    public ResponseEntity<?> updateAccount(Map<String,String> request){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication==null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser"))
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//        String email = authentication.getName();
//
//        userService.updateAccount(email);
//    }

}
