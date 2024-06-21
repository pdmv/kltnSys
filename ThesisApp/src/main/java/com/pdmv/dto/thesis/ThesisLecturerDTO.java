/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.thesis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.ThesisLecturer;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisLecturerDTO {
    private Integer lecturerId;
    private String firstName;
    private String lastName;
    private String email;
    
    public static ThesisLecturerDTO toThesisLecturerDTO(ThesisLecturer thesisLecturer) {
        ThesisLecturerDTO dto = new ThesisLecturerDTO();
        dto.setLecturerId(thesisLecturer.getLecturerId().getId());
        dto.setFirstName(thesisLecturer.getLecturerId().getFirstName());
        dto.setLastName(thesisLecturer.getLecturerId().getLastName());
        dto.setEmail(thesisLecturer.getLecturerId().getEmail());
        return dto;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
