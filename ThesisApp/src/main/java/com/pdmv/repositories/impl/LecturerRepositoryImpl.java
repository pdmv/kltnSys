/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Lecturer;
import com.pdmv.repositories.LecturerRepository;
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
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
public class LecturerRepositoryImpl implements LecturerRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(Lecturer lecturer) {
        Session s = this.factory.getObject().getCurrentSession();
        if (lecturer.getId() == null) {
            s.save(lecturer);
        } else {
            s.update(lecturer);
        }
    }

    @Override
    public List<Lecturer> getLecturers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Lecturer> criteriaQuery = criteriaBuilder.createQuery(Lecturer.class);
        Root<Lecturer> root = criteriaQuery.from(Lecturer.class);

        root.fetch("facultyId", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        String type = params.getOrDefault("type", "");
        String kw = params.getOrDefault("kw", "");

        if (!type.isBlank()) {
            switch (type) {
                case "faculty":
                    try {
                        int facultyId = Integer.parseInt(kw);
                        predicates.add(criteriaBuilder.equal(root.get("facultyId").get("id"), facultyId));
                    } catch (NumberFormatException e) {
                        return new ArrayList<>();
                    }
                    break;
                case "name":
                    if (!kw.isBlank()) {
                        predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(root.get("firstName"), "%" + kw + "%"),
                            criteriaBuilder.like(root.get("lastName"), "%" + kw + "%")
                        ));
                    }
                    break;
                case "id":
                    try {
                        int id = Integer.parseInt(kw);
                        predicates.add(criteriaBuilder.equal(root.get("id"), id));
                    } catch (NumberFormatException e) {
                        return new ArrayList<>();
                    }
                    break;
                default:
                    break;
            }
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        Query<Lecturer> query = s.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Lecturer getLecturerById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Lecturer) s.get(Lecturer.class, id);
    }
    
}
