package com.example.Attendance_Audit_System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="*")
public class UserController 
{
    @Autowired
    UserRepository repo;
    
    //creating account temporary
    @PostMapping("/login")
    public ResponseEntity<String> UserLogin(@Valid @RequestBody Users newUser)//new user contains email and password of the person trying to login
    {
        if(!repo.existsByEmail(newUser.getEmail()))// making sure user exists
        {
            return  ResponseEntity.badRequest().body("User does not Exist.");
        }

        Long userid=repo.findIdByEmailAndPassword(newUser.getEmail(), newUser.getPassword());

        if(userid == null) // if use exists by email meaning userid will only be null if password is incorrect
        {
            return ResponseEntity.badRequest().body("Invalid password.");
        }

        return ResponseEntity.ok().body("Login Successful!");

    }

}
