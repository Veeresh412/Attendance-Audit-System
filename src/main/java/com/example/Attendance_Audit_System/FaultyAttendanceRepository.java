package com.example.Attendance_Audit_System;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultyAttendanceRepository extends JpaRepository<FaultyAttendance,Long> 
{
    
}
