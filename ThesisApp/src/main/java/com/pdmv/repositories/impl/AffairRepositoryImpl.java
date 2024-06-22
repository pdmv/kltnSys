/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Affair;
import com.pdmv.repositories.AffairRepository;
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
public class AffairRepositoryImpl implements AffairRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public void addOrUpdate(Affair affair) {
        Session s = this.factory.getObject().getCurrentSession();
        if (affair.getId() == null) {
            s.save(affair);
        } else {
            s.update(affair);
        }
    }

    @Override
    public List<Affair> getAffairs(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Affair> criteriaQuery = criteriaBuilder.createQuery(Affair.class);
        Root<Affair> root = criteriaQuery.from(Affair.class);

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

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Affair> q = s.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }

    @Override
    public Affair getAffairById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Affair) s.get(Affair.class, id);
    }

    @Override
    public Affair getAffairByAccountId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Affair WHERE accountId.id = :accountId");
        q.setParameter("accountId", id);
        
        try {
            return (Affair) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
