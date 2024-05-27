/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Faculty;
import com.pdmv.repositories.FacultyRepository;
import java.util.List;
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
public class FacultyRepositoryImpl implements FacultyRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(Faculty faculty) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if (faculty.getId() == null) {
            s.save(faculty);
        } else {
            s.update(faculty);
        }
    }

    @Override
    public Faculty getFacultyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Faculty.class, id);
    }

    @Override
    public List<Faculty> getFaculties(String kw) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Faculty> query = builder.createQuery(Faculty.class);
        Root<Faculty> root = query.from(Faculty.class);

        if (kw != null && !kw.isEmpty()) {
            Predicate namePredicate = builder.like(root.get("name"), "%" + kw + "%");
            query.where(namePredicate);
        }

        Query<Faculty> q = session.createQuery(query);
        return q.getResultList();
    }
    
}
