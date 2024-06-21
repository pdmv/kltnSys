/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.Lecturer;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class LecturerDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    
    public static LecturerDTO toLecturerDTO(Lecturer lecturer) {
        LecturerDTO l = new LecturerDTO();
        
        l.setId(lecturer.getId());
        l.setFirstName(lecturer.getFirstName());
        l.setLastName(lecturer.getLastName());
        l.setEmail(lecturer.getEmail());
        
        return l;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
