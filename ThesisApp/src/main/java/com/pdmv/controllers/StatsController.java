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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 * @author tid83
 */
@Controller
public class StatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String statsView(Model model, @RequestParam Map<String, String> params) {
        String facultyId = params.get("facultyId"); // Chỉnh sửa để lấy facultyId từ params

        // Thống kê điểm trung bình khoá luận theo từng niên khoá của một khoa
        model.addAttribute("avgScoresByYear", statsService.statsAvgScoresByYear(facultyId));

        // Thống kê tần suất tham gia làm khoá luận theo từng ngành của từng niên khoá của một khoa
        model.addAttribute("thesisParticipation", statsService.statsThesisParticipationByMajor(facultyId));

        return "stats";
    }
}