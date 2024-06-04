/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Student;
import com.pdmv.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
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
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(Student student) {
        Session s = this.factory.getObject().getCurrentSession();
        if (student.getId() == null) {
            s.save(student);
        } else {
            s.update(student);
        }
    }

    @Override
    public List<Student> getStudents(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);

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
                case "major":
                    try {
                        int majorId = Integer.parseInt(kw);
                        predicates.add(criteriaBuilder.equal(root.get("majorId").get("id"), majorId));
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

        Query<Student> query = s.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Student getStudentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Student) s.get(Student.class, id);
    }

    @Override
    public Student getStudentByAccountId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Student WHERE accountId.id = :accountId");
        q.setParameter("accountId", id);

        try {
            return (Student) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
