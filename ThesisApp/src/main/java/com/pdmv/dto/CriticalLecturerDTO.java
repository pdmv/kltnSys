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
public class CriticalLecturerDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    
    public static CriticalLecturerDTO toCriticalLecturerDTO(Lecturer criticalLecturer) {
        CriticalLecturerDTO dto = new CriticalLecturerDTO();
        dto.setId(criticalLecturer.getId());
        dto.setFirstName(criticalLecturer.getFirstName());
        dto.setLastName(criticalLecturer.getLastName());
        dto.setEmail(criticalLecturer.getEmail());
        return dto;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
