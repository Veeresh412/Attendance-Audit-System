package com.example.Attendance_Audit_System.controller;

import com.example.Attendance_Audit_System.entity.Users;
import com.example.Attendance_Audit_System.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/login")
@CrossOrigin(origins="*")
public class UserController 
{
    @Autowired
    UserRepository repo;

    @PostMapping
    public ResponseEntity<String> userLogin(@RequestBody Users newUser)
    {
        if(!repo.existsByEmail(newUser.getEmail()))
        {
            return ResponseEntity.badRequest().body("User does NOT Exist");
        }


        if(repo.findIdByEmailAndPassword(newUser.getEmail(), newUser.getPassword())== null)
        {
            return ResponseEntity.badRequest().body("Incorrect Password"); // if email exists means password is wrong
        }

        return ResponseEntity.ok().body("Login Sucessful!");    
    }

}
