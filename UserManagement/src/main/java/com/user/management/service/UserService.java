package com.user.management.service;

import com.user.management.dto.KafkaTopicDto;
import com.user.management.entity.Users;
import com.user.management.jwt.JWTService;
import com.user.management.repo.UserDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserDetailsRepo repo;


    @Autowired
    private KafkaProducer kafkaProducer;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ResponseEntity<Object> register(Users user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));
            user = repo.saveAndFlush(user);
            kafkaProducer.sendMessage(new KafkaTopicDto("REGISTER", user.getUsername()));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error to sign in ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> loginuser(Object principal) {
        try {
            UserDetails userDetails = (UserDetails) principal;
            String token = jwtService.generateToken(userDetails.getUsername());
            kafkaProducer.sendMessage(new KafkaTopicDto("LOGIN", userDetails.getUsername()));
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>("Error to sign in ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> updateUserRole(Users user) {

        try {
            Users existingUser = repo.findByUsername(user.getUsername());
            if (existingUser != null) {
                existingUser.setRoles(user.getRoles().stream().map(i -> "ROLE_" + i).collect(Collectors.toSet()));
               user= repo.saveAndFlush(existingUser);
                kafkaProducer.sendMessage(new KafkaTopicDto("UPDATE_ROLE",user.getUsername() ));
                return new ResponseEntity<>("User roles updated", HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Error to update roles,user details not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error to update roles " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<Object> updatePassword(Users user) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Users existingUser = repo.findByUsername(userName);
            if (existingUser != null) {
                existingUser.setPassword(encoder.encode(user.getPassword()));
                existingUser.setLogout(Boolean.TRUE);
                repo.saveAndFlush(existingUser);
                kafkaProducer.sendMessage(new KafkaTopicDto("UPDATE_PASSWORD",user.getUsername() ));
                return new ResponseEntity<>("User roles updated", HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Error to update roles,user details not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error to update roles " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> fetchUserDetails(String userName) {
        try {
            Users existingUser = repo.findByUsername(userName);
            if (existingUser != null) {
                kafkaProducer.sendMessage(new KafkaTopicDto("ADMIN_FETCH",existingUser.getUsername() ));
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User details not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error to fetch user Details " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}