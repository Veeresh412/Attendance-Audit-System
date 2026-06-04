package com.example.Attendance_Audit_System;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Users temp(@Valid @RequestBody Users newUser)//new user contains email and password of the person trying to login
    {
        
    }

}
