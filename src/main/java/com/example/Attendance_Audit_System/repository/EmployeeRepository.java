package com.example.Attendance_Audit_System.repository;

import com.example.Attendance_Audit_System.entity.Employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long> {
    
    // Spring Boot is smart enough to generate the SQL for these automatically based on the method names!
    boolean existsByEmail(String email);
    boolean existsByNumber(String number);
    
    Employees findByEmail(String email);
}
