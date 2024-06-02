/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Student;
import com.pdmv.repositories.StudentRepository;
import com.pdmv.services.AccountService;
import com.pdmv.services.StudentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public void addOrUpdate(Student student) {
        Account account = student.getAccountId();
        account.setRole("STUDENT");
        
        if (student.getId() != null) {
            account.setActive(student.getActive());
        }
        
        this.accountService.addOrUpdate(account);
        student.setAccountId(this.accountService.getAccountByUsername(account.getUsername()));
        this.studentRepo.addOrUpdate(student);
    }

    @Override
    public List<Student> getStudents(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.studentRepo.getStudents(params);
    }

    @Override
    public Student getStudentById(int id) {
        return this.studentRepo.getStudentById(id);
    }

    @Override
    public Student getStudentByAccountId(int id) {
        return this.studentRepo.getStudentByAccountId(id);
    }
    
}
