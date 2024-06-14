/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.Student;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class StudentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    
    public static StudentDTO toStudentDTO(Student student) {
        StudentDTO s = new StudentDTO();
        
        s.setId(student.getId());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setEmail(student.getEmail());
        
        return s;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
