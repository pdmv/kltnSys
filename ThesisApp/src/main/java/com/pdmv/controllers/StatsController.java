/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author tid83
 */
@Controller
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String showStats(Model model) {
        model.addAttribute("avgScoresBySchoolYearAndFaculty", statsService.getAvgScoresBySchoolYearAndFaculty());
        model.addAttribute("participationFrequencyByMajorFacultyAndSchoolYear", statsService.getParticipationFrequencyByMajorFacultyAndSchoolYear());
        return "stats";
    }
}
