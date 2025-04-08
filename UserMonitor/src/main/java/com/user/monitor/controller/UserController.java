package com.user.monitor.controller;



import com.user.monitor.entity.UsersInfo;
import com.user.monitor.service.KafkaConsumer;
import com.user.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KafkaConsumer kafkaProducerService;

    @Autowired
    private UserService userService;


    @PostMapping("/register/user")
    public Object createCustomer(@RequestBody UsersInfo userInfo)
    {
        try{
            return  userService.register(userInfo);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to register User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/user")
    public Object loginCustomer(Authentication authentication)
    {
        try{
            return userService.loginuser(authentication.getPrincipal());
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to register User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch/{username}")
    public ResponseEntity<Object> createCustomer(@PathVariable("username") String userName)
    {
        try{
            return
                    userService.fetchUserDetails(userName);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to fetch User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/userRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateUserRole(@RequestBody UsersInfo user)
    {
        try{
            return userService.updateUserRole(user);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/user")
    public ResponseEntity<Object> updateUser(@RequestBody UsersInfo user)
    {
        try{
            return userService.updatePassword(user);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
