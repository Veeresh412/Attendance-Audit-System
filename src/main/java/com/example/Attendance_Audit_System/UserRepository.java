package com.example.Attendance_Audit_System;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{
    Users findByEmail(String email);

    @Query("SELECT u.userId FROM Users u WHERE u.email = :email AND u.password = :password")
    Long findIdByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    boolean existsByEmail(String email);
}
