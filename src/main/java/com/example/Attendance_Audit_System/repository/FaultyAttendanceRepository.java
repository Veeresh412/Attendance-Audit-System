package com.example.Attendance_Audit_System.repository;

import com.example.Attendance_Audit_System.entity.FaultyAttendance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultyAttendanceRepository extends JpaRepository<FaultyAttendance,Long> 
{
    
}
