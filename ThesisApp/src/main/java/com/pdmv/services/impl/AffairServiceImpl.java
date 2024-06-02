/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.repositories.AffairRepository;
import com.pdmv.services.AccountService;
import com.pdmv.services.AffairService;
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
public class AffairServiceImpl implements AffairService {
    @Autowired
    private AffairRepository affairRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public void addOrUpdate(Affair affair) {
        Account account = affair.getAccountId();
        account.setRole("AFFAIR");
        
        if (affair.getId() != null) {
            account.setActive(affair.getActive());
        }
        
        this.accountService.addOrUpdate(account);
        affair.setAccountId(this.accountService.getAccountByUsername(account.getUsername()));
        this.affairRepo.addOrUpdate(affair);
    }

    @Override
    public List<Affair> getAffairs(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.affairRepo.getAffairs(params);
    }

    @Override
    public Affair getAffairById(int id) {
        return this.affairRepo.getAffairById(id);
    }

    @Override
    public Affair getAffairByAccountId(int id) {
        return this.affairRepo.getAffairByAccountIf(id);
    }
    
}
