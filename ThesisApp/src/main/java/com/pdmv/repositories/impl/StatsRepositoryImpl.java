/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *
 * @author tid83
 */
@Repository
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Object[]> findAvgScoresBySchoolYearAndFaculty() {
        String query = "SELECT school_year_id, faculty_id, AVG(avg_score) " +
                       "FROM thesis " +
                       "GROUP BY school_year_id, faculty_id";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Object[]{
                rs.getInt("school_year_id"),
                rs.getInt("faculty_id"),
                rs.getDouble("AVG(avg_score)")
        });
    }

    @Override
    public List<Object[]> findParticipationFrequencyByMajorFacultyAndSchoolYear() {
        String query = "SELECT major_id, faculty_id, school_year_id, COUNT(student_id) " +
                       "FROM thesis " +
                       "GROUP BY major_id, faculty_id, school_year_id";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Object[]{
                rs.getInt("major_id"),
                rs.getInt("faculty_id"),
                rs.getInt("school_year_id"),
                rs.getInt("COUNT(student_id)")
        });
    }
}