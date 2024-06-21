/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.pdmv.pojo.CouncilCriterion;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class CouncilCriterionDTO {
    private int id;
    private float weight;
    private String name;
    
    public static CouncilCriterionDTO toCouncilCriterionDTO(CouncilCriterion c) {
        CouncilCriterionDTO dto = new CouncilCriterionDTO();
        
        dto.setId(c.getCriterionId().getId());
        dto.setName(c.getCriterionId().getName());
        dto.setWeight(c.getWeight());
        
        return dto;
    }
}