/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.pdmv.pojo.SchoolYear;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class SchoolYearDTO {
    private Integer id;
    private Integer startYear;
    private Integer endYear;
    
    public static SchoolYearDTO toSchoolYearDTO(SchoolYear schoolYear) {
        SchoolYearDTO s = new SchoolYearDTO();
        
        s.setId(schoolYear.getId());
        s.setStartYear(schoolYear.getStartYear());
        s.setEndYear(schoolYear.getEndYear());
        
        return s;
    }
}
