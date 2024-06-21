/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdmv.dto.SchoolYearDTO;
import com.pdmv.pojo.Council;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class SimpleCouncilDTO {
    private int id;
    private String name;
    private SchoolYearDTO schoolYearId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date meetingDate;
    private String status;
    
    public static SimpleCouncilDTO toSimpleCouncilDTO(Council council) {
        SimpleCouncilDTO dto = new SimpleCouncilDTO();
        
        dto.setId(council.getId());
        dto.setName(council.getName());
        dto.setSchoolYearId(SchoolYearDTO.toSchoolYearDTO(council.getSchoolYearId()));
        dto.setMeetingDate(council.getMeetingDate());
        dto.setStatus(council.getStatus());
        
        return dto;
    }
}
