package com.example.Attendance_Audit_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AttendanceAuditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceAuditSystemApplication.class, args);
	}

}
