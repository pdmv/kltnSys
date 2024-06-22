/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Class;
import com.pdmv.repositories.ClassRepository;
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
public class ClassRepositoryImpl implements ClassRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public void addOrUpdate(Class myClass) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if (myClass.getId() == null) {
            s.save(myClass);
        } else {
            s.update(myClass);
        }
    }

    @Override
    public Class getClassById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        
        return s.get(Class.class, id);
    }

    @Override
    public List<Class> getClasses(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Class> criteriaQuery = criteriaBuilder.createQuery(Class.class);
        Root<Class> root = criteriaQuery.from(Class.class);

        root.fetch("facultyId", JoinType.LEFT);
        root.fetch("majorId", JoinType.LEFT);

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
                        predicates.add(criteriaBuilder.like(root.get("name"), "%" + kw + "%"));
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

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Class> q = s.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }
    
}
