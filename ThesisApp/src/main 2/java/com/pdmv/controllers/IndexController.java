package com.pdmv.controllers;


import com.pdmv.pojo.Account;
import com.pdmv.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phamdominhvuong
 */
@Controller
@ControllerAdvice
public class IndexController {
    @Autowired
    private AccountService accountService;
//    @Autowired
//    private EmailService emailService;
    
    public String getCurrentUserAvatar(AccountService accountService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            Account account = accountService.getAccountByUsername(username);
            if (account != null) {
                return account.getAvatar();
            }
        }
        return null;
    }
    
    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("userAvatar", this.getCurrentUserAvatar(accountService));
    }
    
    @RequestMapping("/")
    public String index() {
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("name", "Test emial");
//        variables.put("message", "Chào mừng bạn đến với hệ thống quản lý luận văn!");
//
//        emailService.sendEmail("phamdominhvuong07112003@gmail.com", "Email chào mừng", "test", variables);
        
        return "index";
    }
    
}