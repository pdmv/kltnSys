/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.repositories.AdminRepository;
import com.pdmv.services.AccountService;
import com.pdmv.services.AdminService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public void addOrUpdate(Admin admin) {
        Account account = admin.getAccountId();
        account.setRole("ADMIN");
        
        if (admin.getId() != null) {
            account.setActive(admin.getActive());
        }
        
        this.accountService.addOrUpdate(account);
        admin.setAccountId(this.accountService.getAccountByUsername(account.getUsername()));
        this.adminRepo.addOrUpdate(admin);
    }

    @Override
    public List<Admin> getAdmins(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.adminRepo.getAdmins(params);
    }

    @Override
    public Admin getAdminById(int id) {
        return this.adminRepo.getAdminById(id);
    }

    @Override
    public Admin getAdminByAccountId(int id) {
        return this.adminRepo.getAdminByAccountId(id);
    }
    
}
