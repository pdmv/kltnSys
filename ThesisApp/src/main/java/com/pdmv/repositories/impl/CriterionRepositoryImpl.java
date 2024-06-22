/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Criterion;
import com.pdmv.repositories.CriterionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
@PropertySource("classpath:pagination.properties")
public class CriterionRepositoryImpl implements CriterionRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public int addOrUpdate(Criterion criterion) {
        Session s = this.factory.getObject().getCurrentSession();
        if (criterion.getId() == null) {
            s.save(criterion);
        } else {
            s.update(criterion);
        }
        
        return criterion.getId();
    }

    @Override
    public List<Criterion> list(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Criterion> criteriaQuery = criteriaBuilder.createQuery(Criterion.class);
        Root<Criterion> root = criteriaQuery.from(Criterion.class);
        
        root.fetch("affairId", JoinType.LEFT);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (params != null) {
            try {
                String facultyId = params.get("facultyId");
                if (facultyId != null && !facultyId.isEmpty()) {
                    int id = Integer.parseInt(facultyId);
                    predicates.add(criteriaBuilder.equal(root.get("facultyId").get("id"), id));
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid facultyId: " + e.getMessage()); 
            }

            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + name + "%")
                ));
            }

            try {
                String id = params.get("id");
                if (id != null && !id.isEmpty()) {
                    int criterionId = Integer.parseInt(id); 
                    predicates.add(criteriaBuilder.equal(root.get("id"), criterionId)); 
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid criterion ID: " + e.getMessage());
            }
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))); 
        }

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Criterion> q = s.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }

    @Override
    public Criterion getCriterionById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Criterion) s.get(Criterion.class, id);
    }
    
}
