/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Major;
import com.pdmv.repositories.MajorRepositoty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
public class MajorRepositoryImpl implements MajorRepositoty {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(Major major) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if (major.getId() == null) {
            s.save(major);
        } else {
            s.update(major);
        }
    }

    @Override
    public Major getMajorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Major.class, id);
    }

    @Override
    public List<Major> getMajors(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Major> criteriaQuery = criteriaBuilder.createQuery(Major.class);
        Root<Major> root = criteriaQuery.from(Major.class);

        List<Predicate> predicates = new ArrayList<>();

        String type = params.getOrDefault("type", "");
        String kw = params.getOrDefault("kw", "");

        if (!type.isBlank()) {
            if (type.equals("faculty")) {
                try {
                    int facultyId = Integer.parseInt(kw);
                    predicates.add(criteriaBuilder.equal(root.get("facultyId"), facultyId));
                } catch (NumberFormatException e) {
                    return new ArrayList<>();
                }
            } else if (type.equals("name")) {
                if (!kw.isBlank()) {
                    predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + kw + "%")
                    ));
                }
            }
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        Query<Major> query = s.createQuery(criteriaQuery);
        return query.getResultList();
    }

    
}
