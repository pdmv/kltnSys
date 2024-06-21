/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.dto.council.CreateCouncilDTO;
import com.pdmv.dto.council.MarkDTO;
import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.pojo.Council;
import com.pdmv.repositories.CouncilRepository;
import com.pdmv.services.CouncilService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class CouncilServiceImpl implements CouncilService {
    @Autowired
    private CouncilRepository councilRepo;

    @Override
    public Integer addCouncil(CreateCouncilDTO dto) {
        return this.councilRepo.addCouncil(dto);
    }

    @Override
    public List<SimpleCouncilDTO> getLists(Map<String, String> params) {
        return this.councilRepo.getLists(params);
    }

    @Override
    public Council getCouncilById(int id) {
        return this.councilRepo.getCouncilById(id);
    }

    @Override
    public void mark(MarkDTO mark) {
        this.councilRepo.mark(mark);
    }

    @Override
    public MarkDTO getMarks(int councilId, int thesisId, int lecturerId) {
        return this.councilRepo.getMarks(councilId, thesisId, lecturerId);
    }

    @Override
    public void blockCouncil(int councilId) {
        this.councilRepo.blockCouncil(councilId);
    }

    @Override
    public void unblockCouncil(int councilId) {
        this.councilRepo.unblockCouncil(councilId);
    }
    
}