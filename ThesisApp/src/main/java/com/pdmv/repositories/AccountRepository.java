/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories;

import com.pdmv.pojo.Account;

/**
 *
 * @author phamdominhvuong
 */
public interface AccountRepository {
    void addAccount(Account account);
    Account getAccountByUsername(String username);
    boolean authAccount(String username, String password);
}
