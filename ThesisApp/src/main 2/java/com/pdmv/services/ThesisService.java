/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.dto.thesis.ThesisDTO;
import com.pdmv.dto.thesis.CreateThesisDTO;
import com.pdmv.dto.thesis.ThesisDetailsDTO;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author phamdominhvuong
 */
public interface ThesisService {
    ThesisDetailsDTO addOrUpdate(CreateThesisDTO thesis);
    ThesisDetailsDTO getThesisById(int id);
    List<ThesisDTO> getLists(Map<String, String> params);
    void submitReportFile(int id, MultipartFile file);
}
