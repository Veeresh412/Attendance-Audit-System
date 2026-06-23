package com.example.Attendance_Audit_System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Attendance_Audit_System.entity.FaultyAttendance;
import com.example.Attendance_Audit_System.repository.FaultyAttendanceRepository;

@RestController
@RequestMapping("/api/faulty-attendance")
@CrossOrigin(origins = "*")
public class FaultyAttendanceController {

    @Autowired
    private FaultyAttendanceRepository repo;

    @GetMapping
    public List<FaultyAttendance> viewFaultyLogs() {
        return repo.findAll();
    }

    @DeleteMapping
    public void temp()
    {
        repo.deleteAll();
    }
}