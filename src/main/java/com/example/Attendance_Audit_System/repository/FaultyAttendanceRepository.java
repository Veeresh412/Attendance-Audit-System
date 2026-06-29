package com.example.Attendance_Audit_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Attendance_Audit_System.entity.FaultyAttendance;

public interface FaultyAttendanceRepository extends JpaRepository<FaultyAttendance,Long> 
{
    
}
