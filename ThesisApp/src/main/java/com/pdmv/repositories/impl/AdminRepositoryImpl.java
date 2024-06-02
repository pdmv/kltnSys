/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Admin;
import com.pdmv.repositories.AdminRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
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
public class AdminRepositoryImpl implements AdminRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(Admin admin) {
        Session s = this.factory.getObject().getCurrentSession();
        if (admin.getId() == null) {
            s.save(admin);
        } else {
            s.update(admin);
        }
    }

    @Override
    public List<Admin> getAdmins(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Admin> q = b.createQuery(Admin.class);
        Root<Admin> r = q.from(Admin.class);
        
        List<Predicate> predicates = new ArrayList<>();

        String type = params.getOrDefault("type", "");
        String kw = params.getOrDefault("kw", "");
        if (!type.isBlank() && !kw.isBlank()) {
            if (type.equals("id")) {
                try {
                    int id = Integer.parseInt(kw);
                    predicates.add(b.equal(r.get("id"), id));
                } catch (NumberFormatException e) {
                    return new ArrayList<>();
                }
            } else if (type.equals("name")) {
                predicates.add(b.or(
                    b.like(r.get("firstName"), "%" + kw + "%"),
                    b.like(r.get("lastName"), "%" + kw + "%")
                ));
            }
        }

        if (!predicates.isEmpty()) {
            q.where(predicates.toArray(new Predicate[0]));
        }

        Query<Admin> query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Admin getAdminById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Admin) s.get(Admin.class, id);
    }

    @Override
    public Admin getAdminByAccountId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        
        Query q = s.createQuery("FROM Admin WHERE accountId.id = :accountId");
        q.setParameter("accountId", id);

        try {
            return (Admin) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
