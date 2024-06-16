/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pdmv.services;

import java.util.List;

/**
 *
 * @author tid83
 */
public interface StatsService {
    List<Object[]> statsAvgScoresByYear(String facultyId);

    List<Object[]> statsThesisParticipationByMajor(String facultyId);
}
