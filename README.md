# Attendance Audit System

Welcome to the Attendance Audit System! This is a web-based application built to help HR teams smoothly process, validate, and manage employee attendance records. 

If you've ever had to manually sift through massive spreadsheets of clock-in and clock-out times, looking for missing punches or formatting errors, you know how painful that can be. This system is designed to automate that heavy lifting.

## What It Does

At its core, this application allows an HR user to upload Excel sheets containing employee attendance data. Instead of just saving the data blindly, the system meticulously scans every single row. It flags invalid data, processes the valid records asynchronously, and stores everything securely in a database.

### Key Features

- **Excel File Processing:** Drag, drop, and upload standard Excel files. The system handles the parsing behind the scenes.
- **Smart Validations:** 
  - Checks if a clock-out time accidentally comes *before* a clock-in time.
  - Flags missing punches (e.g., someone clocked in but forgot to clock out).
  - Ensures employee IDs actually exist in the system.
- **Asynchronous Heavy-Lifting:** Big files shouldn't freeze the browser. The system processes files in the background, keeping the user interface snappy and responsive.
- **Database Integration:** All processed and validated records are safely stored in a PostgreSQL database for reporting and future audits.

## Tech Stack

- **Backend:** Java & Spring Boot (REST API, Asynchronous processing)
- **Database:** PostgreSQL (with Spring Data JPA)
- **Frontend:** HTML, CSS, JavaScript (Vanilla JS for dynamic UI interactions)
- **File Parsing:** Apache POI (for Excel processing)

*(More details on setup and running the project locally will be added here as development progresses!)*
