/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.pdmv.pojo.CouncilThesis;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class CouncilThesisDTO {
    private int id;
    private String name;
    
    public static CouncilThesisDTO toCouncilThesisDTO(CouncilThesis t) {
        CouncilThesisDTO dto = new CouncilThesisDTO();
        
        dto.setId(t.getThesisId().getId());
        dto.setName(t.getThesisId().getName());
        
        return dto;
    }
}
