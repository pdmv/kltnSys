/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.thesis;

import com.pdmv.dto.thesis.ThesisLecturerDTO;
import com.pdmv.dto.thesis.ThesisStudentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdmv.dto.FacultyDTO;
import com.pdmv.dto.MajorDTO;
import com.pdmv.dto.SchoolYearDTO;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisDetailsDTO {
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
    private SchoolYearDTO schoolYearId;
    private Set<ThesisLecturerDTO> thesisLecturerSet;
    private Set<ThesisStudentDTO> thesisStudentSet;
    private CriticalLecturerDTO criticalLecturerId;
    private ThesisAffairDTO affairId;
    private FacultyDTO facultyId;
    private MajorDTO majorId;
    
    public static ThesisDetailsDTO toThesisDetailsDTO(Thesis thesis) {
        ThesisDetailsDTO dto = new ThesisDetailsDTO();

        dto.setId(thesis.getId());
        dto.setName(thesis.getName());
        dto.setReportFile(thesis.getReportFile());
        dto.setStartDate(thesis.getStartDate());
        dto.setEndDate(thesis.getEndDate());
        dto.setExpDate(thesis.getExpDate());
        dto.setAvgScore(thesis.getAvgScore());
        dto.setComment(thesis.getComment());
        dto.setStatus(thesis.getStatus());
        dto.setCreatedDate(thesis.getCreatedDate());
        dto.setUpdatedDate(thesis.getUpdatedDate());
        dto.setActive(thesis.getActive());
        dto.setFacultyId(FacultyDTO.toFacultyDTO(thesis.getFacultyId()));
        dto.setMajorId(MajorDTO.toMajorDTO(thesis.getMajorId()));

        if (thesis.getSchoolYearId() != null) {
            dto.setSchoolYearId(SchoolYearDTO.toSchoolYearDTO(thesis.getSchoolYearId()));
        }
        
        Set<ThesisLecturerDTO> lecturerDTOs = thesis.getThesisLecturerSet().stream()
                                                    .map(ThesisLecturerDTO::toThesisLecturerDTO)
                                                    .collect(Collectors.toSet());
        dto.setThesisLecturerSet(lecturerDTOs);

        Set<ThesisStudentDTO> studentDTOs = thesis.getThesisStudentSet().stream()
                                                  .map(ThesisStudentDTO::toThesisStudentDTO) 
                                                  .collect(Collectors.toSet());
        dto.setThesisStudentSet(studentDTOs);

        if (thesis.getCriticalLecturerId() != null) {
            dto.setCriticalLecturerId(CriticalLecturerDTO.toCriticalLecturerDTO(thesis.getCriticalLecturerId()));
        }

        if (thesis.getAffairId() != null) {
            dto.setAffairId(ThesisAffairDTO.toThesisAffairDTO(thesis.getAffairId()));
        }

        return dto;
    }
}
