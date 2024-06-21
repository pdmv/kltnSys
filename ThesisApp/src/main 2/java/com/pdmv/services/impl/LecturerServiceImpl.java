/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Lecturer;
import com.pdmv.repositories.LecturerRepository;
import com.pdmv.services.AccountService;
import com.pdmv.services.LecturerService;
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
public class LecturerServiceImpl implements LecturerService {
    @Autowired
    private LecturerRepository lecturerRepo;
    @Autowired
    private AccountService accountService;
    
    @Override
    public void addOrUpdate(Lecturer lecturer) {
        Account account = lecturer.getAccountId();
        account.setRole("LECTURER");
        
        if (lecturer.getId() != null) {
            account.setActive(lecturer.getActive());
        }
        
        this.accountService.addOrUpdate(account);
        lecturer.setAccountId(this.accountService.getAccountByUsername(account.getUsername()));
        this.lecturerRepo.addOrUpdate(lecturer);
    }

    @Override
    public List<Lecturer> getLecturers(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return this.lecturerRepo.getLecturers(params);
    }

    @Override
    public Lecturer getLecturerById(int id) {
        return this.lecturerRepo.getLecturerById(id);
    }

    @Override
    public Lecturer getLecturerByAccountId(int id) {
        return this.lecturerRepo.getLecturerByAccountId(id);
    }
    
}
