/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.dto.council.CreateCouncilDTO;
import com.pdmv.dto.council.MarkDTO;
import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.dto.report.CriterionScore;
import com.pdmv.pojo.Council;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface CouncilService {
    Integer addCouncil(CreateCouncilDTO dto);
    List<SimpleCouncilDTO> getLists(Map<String, String> params);
    Council getCouncilById(int id);
    void mark(MarkDTO mark);
    MarkDTO getMarks(int councilId, int thesisId, int lecturerId);
    void blockCouncil(int councilId);
    void unblockCouncil(int councilId);
    List<CriterionScore> getCriterionScoresByThesisId(int thesisId);
}
