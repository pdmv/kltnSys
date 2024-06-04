/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

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
    
    public static ThesisLecturerDTO toThesisLecturerDTO(ThesisLecturer thesisLecturer) {
        ThesisLecturerDTO dto = new ThesisLecturerDTO();
        dto.setLecturerId(thesisLecturer.getLecturerId().getId());
        return dto;
    }
}
