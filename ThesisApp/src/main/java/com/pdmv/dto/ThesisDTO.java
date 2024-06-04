/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisDTO {
    private Integer id;
    private String name;
    private String reportFile;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    private Float avgScore;
    private String comment;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private Boolean active;
    private Integer schoolYearId;
    private Set<ThesisLecturerDTO> thesisLecturerSet;
    private Set<ThesisStudentDTO> thesisStudentSet;
    private Integer criticalLecturerId;
    private Integer affairId;
    
    @JsonIgnore
    private MultipartFile file;
    
    public static ThesisDTO toThesisDTO(Thesis thesis) {
        ThesisDTO thesisDTO = new ThesisDTO();

        thesisDTO.setId(thesis.getId());
        thesisDTO.setName(thesis.getName());
        thesisDTO.setReportFile(thesis.getReportFile());
        thesisDTO.setStartDate(thesis.getStartDate());
        thesisDTO.setEndDate(thesis.getEndDate());
        thesisDTO.setExpDate(thesis.getExpDate());
        thesisDTO.setAvgScore(thesis.getAvgScore());
        thesisDTO.setComment(thesis.getComment());
        thesisDTO.setStatus(thesis.getStatus());
        thesisDTO.setCreatedDate(thesis.getCreatedDate());
        thesisDTO.setUpdatedDate(thesis.getUpdatedDate());
        thesisDTO.setActive(thesis.getActive());

        if (thesis.getSchoolYearId() != null) {
            thesisDTO.setSchoolYearId(thesis.getSchoolYearId().getId());
        }
        
        Set<ThesisLecturerDTO> lecturerDTOs = thesis.getThesisLecturerSet().stream()
                                                    .map(ThesisLecturerDTO::toThesisLecturerDTO)
                                                    .collect(Collectors.toSet());
        thesisDTO.setThesisLecturerSet(lecturerDTOs);

        Set<ThesisStudentDTO> studentDTOs = thesis.getThesisStudentSet().stream()
                                                  .map(ThesisStudentDTO::toThesisStudentDTO) 
                                                  .collect(Collectors.toSet());
        thesisDTO.setThesisStudentSet(studentDTOs);

        if (thesis.getCriticalLecturerId() != null) {
            thesisDTO.setCriticalLecturerId(thesis.getCriticalLecturerId().getId());
        }

        if (thesis.getAffairId() != null) {
            thesisDTO.setAffairId(thesis.getAffairId().getId());
        }

        return thesisDTO;
    }
}
