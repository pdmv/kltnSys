/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Faculty;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class CreateCouncilDTO {
    private String name;
    private int schoolYearId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date meetingDate;
    private Affair affairId;
    private Faculty facultyId;
    private Set<CouncilCriterionDTO> criterions;
    private Set<CouncilLecturerDTO> lecturers;
    private Set<CouncilThesisDTO> theses;
}