package com.example.Attendance_Audit_System;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ExcelProcessingService {

    @Autowired
    private EmployeeRepository employeeRepo;
    
    @Autowired
    private AttendanceRepository attendanceRepo;
    
    @Autowired
    private FaultyAttendanceRepository faultyRepo;

    public String processExcelFile(MultipartFile file) {
        int successCount = 0;
        int faultyCount = 0;

        // DataFormatter magically converts ANY weird Excel cell (Dates, Decimals, etc) into a clean Java String
        DataFormatter formatter = new DataFormatter();

        // Open the uploaded Excel file
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
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
                    LocalDate workday = LocalDate.parse(dateStr);
                    LocalTime clockIn = LocalTime.parse(clockInStr);
                    LocalTime clockOut = LocalTime.parse(clockOutStr);

                    // 4. Calculate the Late Strike (Cutoff is 09:30)
                    LocalTime lateCutoff = LocalTime.of(9, 30);
                    boolean isLate = clockIn.isAfter(lateCutoff);

                    // 5. Build and Save the PERFECT Attendance record
                    Attendance attendance = new Attendance();
                    attendance.setEmployee(employee);
                    attendance.setWorkday(workday);
                    attendance.setClockIn(clockIn);
                    attendance.setClockOut(clockOut);
                    attendance.setIsLate(isLate);

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
            return "Excel Processing Complete! Successful Rows: " + successCount + " | Faulty Rows: " + faultyCount;

        } catch (Exception e) {
            return "Failed to open the Excel file entirely. Error: " + e.getMessage();
        }
    }
}
