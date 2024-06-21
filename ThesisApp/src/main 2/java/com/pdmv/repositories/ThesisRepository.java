/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.dto.thesis.ThesisDTO;
import com.pdmv.dto.thesis.CreateThesisDTO;
import com.pdmv.dto.thesis.ThesisDetailsDTO;
import com.pdmv.pojo.Thesis;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface ThesisRepository {
    int addOrUpdate(CreateThesisDTO thesis);
    Thesis getThesisById(int id);
    ThesisDetailsDTO getThesisDTOById(int id);
    List<ThesisDTO> getLists(Map<String, String> params);
    void submitReportFile(Integer id, String url);
}
