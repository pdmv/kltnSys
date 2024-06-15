/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Criterion;
import com.pdmv.repositories.CriterionRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * @author tid83
 */
@Repository
public class CriterionRepositoryImpl implements CriterionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addOrUpdate(Criterion criterion) {
        if (criterion.getId() == null) {
            entityManager.persist(criterion);
        } else {
            entityManager.merge(criterion);
        }
    }

    @Override
    public Criterion getCriterionById(int id) {
        return entityManager.find(Criterion.class, id);
    }

    @Override
    public List<Criterion> getCriteria() {
        TypedQuery<Criterion> query = entityManager.createNamedQuery("Criterion.findAll", Criterion.class);
        return query.getResultList();
    }
}
