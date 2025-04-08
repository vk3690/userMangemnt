package com.user.management.controller;


import com.user.management.entity.Users;
import com.user.management.service.KafkaProducer;
import com.user.management.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KafkaProducer kafkaProducerService;

    @Autowired
    private UserService userService;


    @PostMapping("/register/user")
    public Object createCustomer(@RequestBody  Users userInfo)
    {
        try{
            return  userService.register(userInfo);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to register User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/user")
    public ResponseEntity<Object> loginCustomer(Authentication authentication)
    {
        try{
            return userService.loginuser(authentication.getPrincipal());
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to sign in User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createCustomer(@PathVariable("username") String userName)
    {
        try{
            return userService.fetchUserDetails(userName);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to fetch User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/userRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateUserRole(@RequestBody Users user)
    {
        try{
            return userService.updateUserRole(user);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/user")
    public ResponseEntity<Object> updateUser(@RequestBody Users user)
    {
        try{
            return userService.updatePassword(user);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
