package com.example.Attendance_Audit_System;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="users")
public class Users 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;


    @NotBlank
    @Email(message = "Must be a valid email")
    @Column(name="email",nullable=false,unique=true)
    private String email;

    @NotBlank(message = "password cannot be empty")
    @Column(nullable=false)
    private String password;


    public Long getUserId()
    {
        return userId;
    }
    public void setUserId(Long userId)
    {
        this.userId=userId;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
}
