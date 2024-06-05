/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.pojo.Account;
import com.pdmv.repositories.AccountRepository;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addOrUpdate(Account account) {
        Session s = this.factory.getObject().getCurrentSession();
        if (account.getId() == null) {
            s.save(account);
        } else {
            s.update(account);
        }
    }

    @Override
    public Account getAccountByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        
        Query q = s.createNamedQuery("Account.findByUsername");
        q.setParameter("username", username);
        
        return (Account) q.getSingleResult();
    }

    @Override
    public boolean authAccount(String username, String password) {
        Account account = this.getAccountByUsername(username);
        
        return this.passwordEncoder.matches(password, account.getPassword()) && account.getActive() == true;
    }

    @Override
    public Account getAccountById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return (Account) s.get(Account.class, id);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Session s = this.factory.getObject().getCurrentSession();
        
        Account acc = this.getAccountByUsername(username);
        if (acc != null && this.authAccount(username, oldPassword)) {
            acc.setPassword(this.passwordEncoder.encode(newPassword));
            s.update(acc);
            return true;
        }
        
        return false;
    }
    
}
