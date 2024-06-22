/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.services.StatsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class ApiStatsController {
    @Autowired
    private StatsService statsService;
    
    @GetMapping("/avg-thesis-scores/")
    public ResponseEntity<Map<String, Double>> getAvgThesisScoresByYear(
            @RequestParam("facultyId") int facultyId) {
        Map<String, Double> avgScores = statsService.getAvgThesisScoresBySchoolYear(facultyId);
        return ResponseEntity.ok(avgScores);
    }

    @GetMapping("/thesis-count-by-major/")
    public ResponseEntity<Map<String, Long>> getThesisCountByMajor(
            @RequestParam("facultyId") int facultyId,
            @RequestParam(value = "schoolYearId", required = false) Integer schoolYearId) {
        Map<String, Long> thesisCounts = statsService.getThesisCountByMajor(facultyId, schoolYearId);
        return ResponseEntity.ok(thesisCounts);
    }
}
