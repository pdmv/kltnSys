/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author phamdominhvuong
 */
public interface AccountService extends UserDetailsService {
    void addOrUpdate(Account account);
    Account getAccountByUsername(String username);
    Account getAccountById(int id);
    boolean authAccount(String username, String password);
    boolean changePassword(String username, String oldPassword, String newPassword);
}
