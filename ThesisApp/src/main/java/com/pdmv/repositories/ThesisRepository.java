/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.CreateThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface ThesisRepository {
    int addOrUpdate(CreateThesisDTO thesis);
    ThesisDetailsDTO getThesisById(int id);
    List<ThesisDTO> getLists(Map<String, String> params);
    void submitReportFile(Integer id, String url);
}
