package com.pdmv.dto.council;

import com.pdmv.pojo.CouncilLecturer;
import lombok.Getter;
import lombok.Setter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class CouncilLecturerDTO {
    private int id;
    private String position;
    private String lastName;
    private String firstName;
    
    public static CouncilLecturerDTO toCouncilLecturerDTO(CouncilLecturer l) {
        CouncilLecturerDTO dto = new CouncilLecturerDTO();
        
        dto.setId(l.getLecturerId().getId());
        dto.setLastName(l.getLecturerId().getLastName());
        dto.setFirstName(l.getLecturerId().getFirstName());
        dto.setPosition(l.getPosition());
        
        return dto;
    }
}
