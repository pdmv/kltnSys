/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Admin;
import com.pdmv.repositories.AdminRepository;
import org.hibernate.Session;
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
    public void addAdmin(Admin admin) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(admin);
    }
    
}
