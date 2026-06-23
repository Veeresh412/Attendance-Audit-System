package com.example.Attendance_Audit_System.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="faulty_attendance")
public class FaultyAttendance 
{
    @Id
    @Column(name="fa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faId;

    // everything below is a String so we can save garbage data without crashing
    @Column(name="emp_id_input")
    private String employeeIdInput;

    @Column(name="day_input")
    private String workdayInput;

    @Column(name="clock_in_input")
    private String clockInInput;

    @Column(name="clock_out_input")
    private String clockOutInput;

    // The most important column: Why did this row fail?
    @Column(name="error_message", length = 500)
    private String errorMessage;

    // Constructors
    public FaultyAttendance() {}

    public FaultyAttendance(String employeeIdInput, String workdayInput, String clockInInput, String clockOutInput, String errorMessage) {
        this.employeeIdInput = employeeIdInput;
        this.workdayInput = workdayInput;
        this.clockInInput = clockInInput;
        this.clockOutInput = clockOutInput;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public Long getFaId() {
        return faId;
    }

    public String getEmployeeIdInput() {
        return employeeIdInput;
    }

    public void setEmployeeIdInput(String employeeIdInput) {
        this.employeeIdInput = employeeIdInput;
    }

    public String getWorkdayInput() {
        return workdayInput;
    }

    public void setWorkdayInput(String workdayInput) {
        this.workdayInput = workdayInput;
    }

    public String getClockInInput() {
        return clockInInput;
    }

    public void setClockInInput(String clockInInput) {
        this.clockInInput = clockInInput;
    }

    public String getClockOutInput() {
        return clockOutInput;
    }

    public void setClockOutInput(String clockOutInput) {
        this.clockOutInput = clockOutInput;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
