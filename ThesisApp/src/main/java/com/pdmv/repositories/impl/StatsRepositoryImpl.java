/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.repositories.StatsRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Double> getAvgThesisScoresBySchoolYear(int facultyId) {
        String hql = "SELECT CONCAT(s.startYear, '-', s.endYear), COALESCE(AVG(t.avgScore), 0) "
                + // Sử dụng COALESCE
                "FROM SchoolYear s "
                + "LEFT JOIN Thesis t ON t.schoolYearId.id = s.id AND t.facultyId.id = :facultyId "
                + "GROUP BY s.id "
                + "ORDER BY s.id";

        Query query = (Query) entityManager.createQuery(hql);
        query.setParameter("facultyId", facultyId);

        List<Object[]> results = query.getResultList();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0], // "startYear-endYear"
                        result -> (Double) result[1] // avgScore
                ));
    }

    @Override
    public Map<String, Long> getThesisCountByMajor(int facultyId, Integer schoolYearId) {
        String hql = "SELECT m.name, COUNT(DISTINCT ts.studentId.id) " +
                 "FROM Thesis t " +
                 "JOIN Major m ON t.majorId.id = m.id " +
                 "JOIN ThesisStudent ts ON t.id = ts.thesisId.id " +
                 "WHERE t.facultyId.id = :facultyId " +
                 "AND (:schoolYearId IS NULL OR t.schoolYearId.id = :schoolYearId) " +
                 "GROUP BY m.id " +
                 "ORDER BY m.name";

    Query query = (Query) entityManager.createQuery(hql);
    query.setParameter("facultyId", facultyId);
    query.setParameter("schoolYearId", schoolYearId); 

    List<Object[]> results = query.getResultList();
    return results.stream()
            .collect(Collectors.toMap(
                    result -> (String) result[0],  // Tên ngành
                    result -> (Long) result[1]    // Số lượng sinh viên
            ));
    }

}
