/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.ThesisStudent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisStudentDTO {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private String email;
    
    public static ThesisStudentDTO toThesisStudentDTO(ThesisStudent thesisStudent) {
        ThesisStudentDTO dto = new ThesisStudentDTO();
        dto.setStudentId(thesisStudent.getStudentId().getId());
        dto.setFirstName(thesisStudent.getStudentId().getFirstName());
        dto.setLastName(thesisStudent.getStudentId().getLastName());
        dto.setEmail(thesisStudent.getStudentId().getEmail());
        return dto;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
