/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface ThesisService {
    void addOrUpdate(ThesisDetailsDTO thesis);
    ThesisDetailsDTO getThesisById(int id);
    List<ThesisDTO> getLists(Map<String, String> params);
}
