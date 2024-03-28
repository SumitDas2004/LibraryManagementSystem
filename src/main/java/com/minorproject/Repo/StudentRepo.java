package com.minorproject.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minorproject.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

    @Query("SELECT s FROM Student s WHERE phoneNumber=?1")
    public Student getByPhone(String phone);

    @Query("SELECT s FROM Student s WHERE email=?1")
    public Student getByEmail(String email);
}
