package com.example.Attendance_Audit_System.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {
    private Long attendanceId;
    private Long empId;
    private LocalDate workday;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private boolean isLate;

    public AttendanceDTO(Long attendanceId, Long empId, LocalDate workday, LocalTime clockIn, LocalTime clockOut, boolean isLate) {
        this.attendanceId = attendanceId;
        this.empId = empId;
        this.workday = workday;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.isLate = isLate;
    }

    public Long getAttendanceId() { return attendanceId; }
    public void setAttendanceId(Long attendanceId) { this.attendanceId = attendanceId; }

    public Long getEmpId() { return empId; }
    public void setEmpId(Long empId) { this.empId = empId; }

    public LocalDate getWorkday() { return workday; }
    public void setWorkday(LocalDate workday) { this.workday = workday; }

    public LocalTime getClockIn() { return clockIn; }
    public void setClockIn(LocalTime clockIn) { this.clockIn = clockIn; }

    public LocalTime getClockOut() { return clockOut; }
    public void setClockOut(LocalTime clockOut) { this.clockOut = clockOut; }

    public boolean getIsLate() { return isLate; }
    public void setIsLate(boolean isLate) { this.isLate = isLate; }
}
