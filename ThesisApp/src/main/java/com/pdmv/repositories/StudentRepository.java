/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.pojo.Student;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author phamdominhvuong
 */
public interface StudentRepository {
    void addOrUpdate(Student student);
    List<Student> getStudents(Map<String, String> params);
    Student getStudentById(int id);
    Student getStudentByAccountId(int id);
}
