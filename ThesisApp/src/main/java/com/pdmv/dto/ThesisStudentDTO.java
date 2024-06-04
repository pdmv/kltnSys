/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

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
    
    public static ThesisStudentDTO toThesisStudentDTO(ThesisStudent thesisStudent) {
        ThesisStudentDTO dto = new ThesisStudentDTO();
        dto.setStudentId(thesisStudent.getStudentId().getId());
        return dto;
    }
}
