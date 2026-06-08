package com.example.Attendance_Audit_System;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins="*")
public class EmployeeController
{
    @Autowired
    EmployeeRepository repo;

    @GetMapping // seeing all employees
    public List<Employees> viewEmployees()
    {
        return repo.findAll();
    }

    // we doin ts later @GetMapping("/{id}") // getting data for one employee

    @PostMapping // adding new employee
    public ResponseEntity<?> addEmployee(@Valid  @RequestBody Employees newEmp)
    {
        if(repo.existsByEmail(newEmp.getEmail()))
        {
            return ResponseEntity.badRequest().body(java.util.Map.of("email", "Email already in use"));
        }
        else if(repo.existsByNumber(newEmp.getNumber()))
        {
            return ResponseEntity.badRequest().body(java.util.Map.of("number", "Number already in use"));
        }

        repo.save(newEmp);
        return ResponseEntity.ok().body("Employee Added Successfully!");
    }
}
