package com.example.Attendance_Audit_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Attendance_Audit_System.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a ORDER BY a.workday ")
    List<Attendance> findAllOrderedByDate();

    @Query(value = "SELECT a.emp_id AS employeeId, e.firstname AS firstName, e.lastname AS lastName, e.email AS email, e.role AS role, COUNT(a.emp_id) AS lateCount " +
                   "FROM attendance a " +
                   "JOIN employees e ON a.emp_id = e.emp_id " +
                   "WHERE a.is_late = true " +
                   "GROUP BY a.emp_id, e.firstname, e.lastname, e.email, e.role", 
           nativeQuery = true)
    List<DefaulterView> findDefaulters();

}
