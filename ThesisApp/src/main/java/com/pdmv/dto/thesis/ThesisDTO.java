/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.thesis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisDTO {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    private String status;
    
    public static ThesisDTO toThesisDTO(Thesis thesis) {
        ThesisDTO thesisDTO = new ThesisDTO();

        thesisDTO.setId(thesis.getId());
        thesisDTO.setName(thesis.getName());
        thesisDTO.setStartDate(thesis.getStartDate());
        thesisDTO.setEndDate(thesis.getEndDate());
        thesisDTO.setExpDate(thesis.getExpDate());
        thesisDTO.setStatus(thesis.getStatus());

        return thesisDTO;
    }
}
