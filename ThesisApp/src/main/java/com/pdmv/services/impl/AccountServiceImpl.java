/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pdmv.pojo.Account;
import com.pdmv.repositories.AccountRepository;
import com.pdmv.services.AccountService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service("userDetailsService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void addAccount(Account account) {
        account.setPassword(this.passwordEncoder.encode(account.getPassword()));
        
        if (account.getFile() != null && !account.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(account.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                
                account.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.accountRepo.addAccount(account);
                
    }

    @Override
    public Account getAccountByUsername(String username) {
        return this.accountRepo.getAccountByUsername(username);
    }

    @Override
    public boolean authAccount(String username, String password) {
        return this.accountRepo.authAccount(username, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.getAccountByUsername(username);
        
        if (account == null) {
            throw new UsernameNotFoundException("Not exists username");
        }
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
    }
    
}
