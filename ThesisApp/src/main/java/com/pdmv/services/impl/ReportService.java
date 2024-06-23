/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.dto.report.Report;
import com.pdmv.pojo.Council;
import com.pdmv.services.CouncilService;
import com.pdmv.services.ThesisService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class ReportService {
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private CouncilService councilService;
    
    public Report report(Integer thesisId) {
        Report result = new Report();
        
        result.setThesis(this.thesisService.getThesisById(thesisId));
        
        Map<String, String> param = new HashMap<>();
        param.put("thesisId", thesisId.toString());
        SimpleCouncilDTO tmp = this.councilService.getLists(param).get(0);
        Council c = this.councilService.getCouncilById(tmp.getId());
        
        result.setCouncil(c);
        result.setScore(this.councilService.getCriterionScoresByThesisId(thesisId));
        result.setPrintedDate(new Date());
        
        return result;
    }
}
