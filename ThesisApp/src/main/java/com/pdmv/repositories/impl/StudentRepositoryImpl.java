/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Student;
import com.pdmv.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
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
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

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
        
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Student> q = s.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize);
        
        return q.getResultList();
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

    @Override
    public List<Student> list(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);

        root.fetch("facultyId", JoinType.LEFT); 
        root.fetch("majorId", JoinType.LEFT); 

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
                    criteriaBuilder.like(root.get("firstName"), "%" + name + "%"),
                    criteriaBuilder.like(root.get("lastName"), "%" + name + "%")
                ));
            }

            try {
                String id = params.get("id");
                if (id != null && !id.isEmpty()) {
                    int studentId = Integer.parseInt(id); 
                    predicates.add(criteriaBuilder.equal(root.get("id"), studentId)); 
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid student ID: " + e.getMessage());
            }

            try {
                String majorId = params.get("majorId"); 
                if (majorId != null && !majorId.isEmpty()) {
                    int id = Integer.parseInt(majorId);
                    predicates.add(criteriaBuilder.equal(root.get("majorId").get("id"), id)); 
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid majorId: " + e.getMessage());
            }
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))); 
        }

        Query<Student> query = s.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
