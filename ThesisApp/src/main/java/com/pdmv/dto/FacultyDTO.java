/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.pdmv.pojo.Faculty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class FacultyDTO {
    private Integer id;
    private String name;
    
    public static FacultyDTO toFacultyDTO(Faculty faculty) {
        FacultyDTO f = new FacultyDTO();
        
        f.setId(faculty.getId());
        f.setName(faculty.getName());
        
        return f;
    }
}
