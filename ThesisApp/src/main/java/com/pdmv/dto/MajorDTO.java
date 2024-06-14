/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.pdmv.pojo.Major;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class MajorDTO {
    private Integer id;
    private String name;
    
    public static MajorDTO toMajorDTO(Major major) {
        MajorDTO m = new MajorDTO();
        
        m.setId(major.getId());
        m.setName(major.getName());
        
        return m;
    }
}
