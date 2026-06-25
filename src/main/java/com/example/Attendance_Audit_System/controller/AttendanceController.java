package com.example.Attendance_Audit_System.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Attendance_Audit_System.entity.Attendance;
import com.example.Attendance_Audit_System.repository.AttendanceRepository;
import com.example.Attendance_Audit_System.repository.DefaulterView;
import com.example.Attendance_Audit_System.service.ExcelProcessingService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository repo;

    @Autowired
    private ExcelProcessingService excelProcessingService;
    
    @GetMapping("/view")
    public List<Attendance> viewAttendance()
    {
        return repo.findAllOrderedByDate();
    }

    @GetMapping("/defaulters")
    public List<DefaulterView> viewdef()
    {
        return repo.findDefaulters();
    }

    @DeleteMapping
    public void temp()
    {
        repo.deleteAll(); 
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadTimesheet(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // 1. Convert the file into raw bytes immediately
            byte[] fileBytes = file.getBytes();
            
            // 2. Pass the bytes to the @Async service
            excelProcessingService.processExcelFile(fileBytes);
            
            // 3. Instantly return a 200 OK response!
            response.put("message", "File uploaded successfully! Processing started in the background.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Failed to read the file: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
