package com.user.monitor.service;


import com.user.monitor.entity.UsersInfo;
import com.user.monitor.jwt.JWTService;
import com.user.monitor.repo.UserDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsersInfo register(UsersInfo user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));
            user=repo.saveAndFlush(user);
            return user;
        }catch (Exception e)
        {
            log.info(" Exception {}",e.getLocalizedMessage());
        }
        return user;
    }

    public String verify(UsersInfo user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public String loginuser(Object principal) {
        try {
            UserDetails userDetails= (UserDetails) principal;
                return jwtService.generateToken(userDetails.getUsername());

        }catch (Exception e)
        {
            log.error("{}",e.getLocalizedMessage());
        }
        return "";
    }

    public ResponseEntity<Object> updateUserRole(UsersInfo user) {

        try{
            UsersInfo existingUser= repo.findByUsername(user.getUsername());
            if(existingUser!=null)
            {
                existingUser.setRoles(user.getRoles().stream().map(i->"ROLE_"+i).collect(Collectors.toSet()));
                repo.saveAndFlush(existingUser);
                return new ResponseEntity<>("User roles updated", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("Error to update roles,user details not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update roles "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<Object> updatePassword(UsersInfo user) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UsersInfo existingUser= repo.findByUsername(userName);
            if(existingUser!=null)
            {
                existingUser.setPassword(encoder.encode(user.getPassword()));
                existingUser.setLogout(Boolean.TRUE);
                repo.saveAndFlush(existingUser);
                return new ResponseEntity<>("User roles updated", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("Error to update roles,user details not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to update roles "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> fetchUserDetails(String userName) {
        try{
            UsersInfo existingUser= repo.findByUsername(userName);
            if(existingUser!=null)
            {
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("User details not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error to fetch user Details "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}