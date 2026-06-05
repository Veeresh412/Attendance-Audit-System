package com.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="employees")
public class Employees 
{
    @Id
    @Column(name="emp_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long empId;

    @NotBlank
    @Column(name="firstname",nullable=false)
    private String firstName;

    @NotBlank
    @Column(name="lastname",nullable=false)
    private String lastName;

    @NotBlank
    @Column(nullable=false,unique=true)
    private Long number;

    @NotBlank
    @Column(nullable=false)
    @Email(message="Must be a valid email")
    private String email;

    @NotBlank
    @Column(nullable=false)
    private String role;

    public Long getEmpId()
    {
        return empId;
    }
    public void setEmpId(Long empId)
    {
        this.empId=empId;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName=firstName;
    }

    public String getLastName()
    {
        return firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }

    public Long getNumber()
    {
        return number;
    }
    public void setNumber(Long number)
    {
        this.number=number;
    }

    public String getRole()
    {
        return role;
    }
    public void setRole(String role)
    {
        this.role=role;
    }
}
