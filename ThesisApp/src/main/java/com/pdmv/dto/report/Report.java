/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.report;

import com.pdmv.dto.thesis.ThesisDetailsDTO;
import com.pdmv.pojo.Council;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class Report {
    private ThesisDetailsDTO thesis;
    private Council council;
    private List<CriterionScore> score;
    private Date printedDate;
}
