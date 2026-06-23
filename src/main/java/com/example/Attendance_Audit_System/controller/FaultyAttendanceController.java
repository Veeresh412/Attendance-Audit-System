package com.example.Attendance_Audit_System.controller;

import com.example.Attendance_Audit_System.entity.FaultyAttendance;
import com.example.Attendance_Audit_System.repository.FaultyAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}