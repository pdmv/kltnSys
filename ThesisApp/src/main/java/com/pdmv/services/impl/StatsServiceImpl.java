/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.repositories.StatsRepository;
import com.pdmv.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author tid83
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public List<Object[]> getAvgScoresBySchoolYearAndFaculty() {
        return statsRepository.findAvgScoresBySchoolYearAndFaculty();
    }

    @Override
    public List<Object[]> getParticipationFrequencyByMajorFacultyAndSchoolYear() {
        return statsRepository.findParticipationFrequencyByMajorFacultyAndSchoolYear();
    }
}
