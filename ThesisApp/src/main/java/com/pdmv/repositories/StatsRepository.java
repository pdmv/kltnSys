/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface StatsRepository {
    Map<String, Double> getAvgThesisScoresBySchoolYear(int facultyId);
    Map<String, Long> getThesisCountByMajor(int facultyId, Integer schoolYearId);
}
