/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.pdmv.pojo.Account;
import com.pdmv.repositories.AccountRepository;
import com.pdmv.services.AccountService;
import java.io.IOException;
import java.util.Arrays;
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
    public void addOrUpdate(Account account) {
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(this.passwordEncoder.encode(account.getPassword()));
        } else if (account.getId() != null) {
            account.setPassword(this.accountRepo.getAccountById(account.getId()).getPassword()); 
        }
       
        if (account.getFile() != null && !account.getFile().isEmpty()) {
            try {
                if (account.getId() != null) {
                    Account exist = this.accountRepo.getAccountById(account.getId());
                    
                    if (exist.getAvatar() != null && !exist.getAvatar().isEmpty()) {
                        String publicId = extractPublicId(account.getAvatar());
                        try {
                            ApiResponse apiResponse = this.cloudinary.api().deleteResources(Arrays.asList(publicId),
                                ObjectUtils.asMap("type", "upload", "resource_type", "image"));
                            System.out.println(apiResponse);
                        } catch (IOException exception) {
                            System.out.println(exception.getMessage());
                        } catch (Exception ex) {
                            Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                Map res = this.cloudinary.uploader().upload(account.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                account.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (account.getId() != null) {
            account.setAvatar(this.accountRepo.getAccountById(account.getId()).getAvatar());
        }
        
        this.accountRepo.addOrUpdate(account);
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

        if (!account.getActive()) {
            throw new UsernameNotFoundException("Tài khoản bị đình chỉ.");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));
        
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
    }

    @Override
    public Account getAccountById(int id) {
        return this.accountRepo.getAccountById(id);
    }
    
    private String extractPublicId(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        int lastDotIndex = url.lastIndexOf('.');
        
        return url.substring(lastSlashIndex + 1, lastDotIndex);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return this.accountRepo.changePassword(username, oldPassword, newPassword);
    }
}
