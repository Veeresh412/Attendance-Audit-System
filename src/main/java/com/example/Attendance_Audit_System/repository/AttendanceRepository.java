package com.example.Attendance_Audit_System.repository;

import com.example.Attendance_Audit_System.entity.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
