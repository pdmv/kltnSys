/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Thesis;
import com.pdmv.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author tid83
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> statsAvgScoresByYear(String facultyId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<Thesis> root = query.from(Thesis.class);
        query.multiselect(root.get("schoolYearId"), builder.avg(root.get("avgScore")));
        query.groupBy(root.get("schoolYearId"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<Object[]> statsThesisParticipationByMajor(String facultyId) {
        String hql = "SELECT thesis.major, COUNT(thesis) "
                + "FROM Thesis thesis "
                + "WHERE thesis.facultyId = :facultyId "
                + "GROUP BY thesis.major";

        TypedQuery<Object[]> query = entityManager.createQuery(hql, Object[].class);
        query.setParameter("facultyId", facultyId);

        return query.getResultList();
    }
}
