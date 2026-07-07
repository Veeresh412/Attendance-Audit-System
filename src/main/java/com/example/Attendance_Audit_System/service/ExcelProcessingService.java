package com.example.Attendance_Audit_System.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Attendance_Audit_System.entity.Attendance;
import com.example.Attendance_Audit_System.entity.Employees;
import com.example.Attendance_Audit_System.entity.FaultyAttendance;
import com.example.Attendance_Audit_System.repository.AttendanceRepository;
import com.example.Attendance_Audit_System.repository.EmployeeRepository;
import com.example.Attendance_Audit_System.repository.FaultyAttendanceRepository;

@Service
public class ExcelProcessingService {

    @Autowired
    private EmployeeRepository employeeRepo;
    
    @Autowired
    private AttendanceRepository attendanceRepo;
    
    @Autowired
    private FaultyAttendanceRepository faultyRepo;

    @Async
    public void processExcelFile(byte[] fileBytes) {
        int successCount = 0;
        int faultyCount = 0;

        // DataFormatter magically converts ANY weird Excel cell (Dates, Decimals, etc) into a clean Java String
        DataFormatter formatter = new DataFormatter();

        // Open the uploaded Excel file
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileBytes))) {
            Sheet sheet = workbook.getSheetAt(0); // Grab the very first sheet

            // Loop through every single row in the sheet
            for (Row row : sheet) {
                // Skip the first row (because Row 0 is just the header labels)
                if (row.getRowNum() == 0) continue;

                // 1. Extract raw String "sticky notes" from the 4 Excel columns
                String empIdStr = formatter.formatCellValue(row.getCell(0));
                String dateStr = formatter.formatCellValue(row.getCell(1));
                String clockInStr = formatter.formatCellValue(row.getCell(2));
                String clockOutStr = formatter.formatCellValue(row.getCell(3));

                // If the row is completely blank, we reached the end of the file, so stop!
                if (empIdStr.isBlank() && dateStr.isBlank()) break;

                // --- START OF VALIDATION ---
                // We wrap this in a try-catch. If ANY validation fails, it instantly jumps to the catch block!
                try {
                    // 2. Try to find the Employee in the database
                    Long empId = Long.parseLong(empIdStr);
                    Optional<Employees> employeeOpt = employeeRepo.findById(empId);
                    
                    if (employeeOpt.isEmpty()) {
                        throw new Exception("Employee ID " + empId + " does not exist in the database!");
                    }
                    Employees employee = employeeOpt.get();

                    // 3. Try to parse the Strings into strict Java Dates and Times
                    // (Assuming your Excel format is YYYY-MM-DD and HH:mm)
                    LocalDate workday = parseFlexibleDate(dateStr);
                    LocalTime clockIn = parseFlexibleTime(clockInStr);
                    LocalTime clockOut = parseFlexibleTime(clockOutStr);

                    // 4. Calculate the Late Strike (Cutoff is 09:30)
                    LocalTime lateCutoff = LocalTime.of(9, 30);
                    boolean isLate = clockIn.isAfter(lateCutoff);

                    // 5. Upsert (Update or Insert) the Attendance record
                    Optional<Attendance> existingOpt = attendanceRepo.findByEmployeeAndWorkday(employee, workday);
                    Attendance attendance;
                    
                    if (existingOpt.isPresent()) {
                        // UPDATE: Overwrite times and status for the existing record
                        attendance = existingOpt.get();
                        attendance.setClockIn(clockIn);
                        attendance.setClockOut(clockOut);
                        attendance.setIsLate(isLate);
                    } else {
                        // INSERT: Create a brand new record
                        attendance = new Attendance();
                        attendance.setEmployee(employee);
                        attendance.setWorkday(workday);
                        attendance.setClockIn(clockIn);
                        attendance.setClockOut(clockOut);
                        attendance.setIsLate(isLate);
                    }

                    attendanceRepo.save(attendance);
                    successCount++;

                } catch (Exception e) {
                    // --- THE TRASH CAN ---
                    // If ANYTHING went wrong (e.g. employee missing, bad date format, text instead of numbers)
                    // Build the Faulty record using your parameterized constructor shortcut and save it!
                    FaultyAttendance badRow = new FaultyAttendance(
                            empIdStr, dateStr, clockInStr, clockOutStr, e.getMessage()
                    );
                    faultyRepo.save(badRow);
                    faultyCount++;
                }
            }
            System.out.println("Excel Processing Complete! Successful Rows: " + successCount + " | Faulty Rows: " + faultyCount);

        } catch (Exception e) {
            System.err.println("Failed to open the Excel file entirely. Error: " + e.getMessage());
        }
    }

    // Helper method to try multiple date formats
    private LocalDate parseFlexibleDate(String dateStr) throws Exception {
        dateStr = dateStr.trim();

        // Common Excel date formats
        String[] patterns = {
            "yyyy-MM-dd", // 2026-06-23 (Strict standard)
            "M/d/yy",     // 6/23/26 (US short)
            "M/d/yyyy",   // 6/23/2026
            "dd-MM-yyyy", // 23-06-2026 (EU standard)
            "d/M/yyyy",   // 23/6/2026
            "MM/dd/yyyy"  // 06/23/2026
        };

        for (String pattern : patterns) {
            try {
                return LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern(pattern));
            } catch (java.time.format.DateTimeParseException ignored) {
                // Silently ignore and try the next pattern
            }
        }
        
        throw new Exception("Unrecognized date format: " + dateStr);
    }

    // Helper method to try multiple time formats
    private LocalTime parseFlexibleTime(String timeStr) throws Exception {
        // Clean up the string (removes invisible spaces and forces uppercase for AM/PM)
        timeStr = timeStr.trim().toUpperCase(); 

        // Our master list of accepted Excel time formats
        String[] patterns = {
            "HH:mm",      // e.g. 17:00 or 09:15 (Standard 24-hour)
            "H:mm",       // e.g. 9:15 (No leading zero 24-hour)
            "h:mm a",     // e.g. 9:15 AM (Standard 12-hour)
            "hh:mm a",    // e.g. 09:15 AM (Leading zero 12-hour)
            "HH:mm:ss",   // e.g. 17:00:00 (Includes seconds)
            "h:mm:ss a"   // e.g. 9:15:00 AM (Includes seconds 12-hour)
        };

        for (String pattern : patterns) {
            try {
                return LocalTime.parse(timeStr, java.time.format.DateTimeFormatter.ofPattern(pattern, java.util.Locale.ENGLISH));
            } catch (java.time.format.DateTimeParseException ignored) {
                // Silently ignore and try the next pattern
            }
        }
        
        throw new Exception("Unrecognized time format: " + timeStr);
    }
}
