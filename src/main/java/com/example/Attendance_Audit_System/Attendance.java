package com.example.Attendance_Audit_System;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="attendance")
public class Attendance 
{
    @Id
    @Column(name="a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

   @ManyToOne
    // This tells PostgreSQL: "Create a foreign key column named emp_id"
    @JoinColumn(name = "emp_id", nullable = false)
    private Employees employee;

    @Column(name="day")
    private LocalDate workday;

    @Column(name="clock_in")
    private LocalTime clockIn;

    @Column(name="clock_Out")
    private LocalTime clockOut;

    @Column(name="is_late",nullable=false)
    private boolean isLate;

    // setters and getters

    public Long getAId()
    {
        return aId; // no setter since it generates automatically
    }

    public void setEmployee(Employees employee)
    {
        this.employee=employee;
    }
    public Employees getEmployee()
    {
        return  employee;
    }

    public void setWorkday(LocalDate workday)
    {
        this.workday=workday;
    }
    public LocalDate getWorkday()
    {
        return workday;
    }
    public void setClockIn(LocalTime clockIn)
    {
        this.clockIn=clockIn;
    }
    public LocalTime getClockIn()
    {
        return clockIn;
    }
     public void setClockOut(LocalTime clockOut)
    {
        this.clockOut=clockOut;
    }
    public LocalTime getClockOut()
    {
        return clockOut;
    }
    public void setIsLate(boolean isLate)
    {
        this.isLate=isLate;
    }
    public boolean getIsLate()
    {
        return isLate;
    }
}
