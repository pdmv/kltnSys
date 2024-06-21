/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.repositories.StatsRepository;
import com.pdmv.services.StatsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public Map<String, Double> getAvgThesisScoresBySchoolYear(int facultyId) {
        return this.statsRepo.getAvgThesisScoresBySchoolYear(facultyId);
    }

    @Override
    public Map<String, Long> getThesisCountByMajor(int facultyId, Integer schoolYearId) {
        return this.statsRepo.getThesisCountByMajor(facultyId, schoolYearId);
    }
    
}
