/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.services.AccountService;
import com.pdmv.services.AdminService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author phamdominhvuong
 */
@Controller
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountService accountService;
    
    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("adminList", this.adminService.getAdmins(params));
        return "admins";
    }
    
    @GetMapping("/add")
    public String createView(Model model) {
        Admin admin = new Admin();
        admin.setAccountId(new Account());
        model.addAttribute("admin", admin);
        return "admin-details";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("admin") @Valid Admin admin, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            return "admin-details";
        }

        try {
            this.adminService.addAdmin(admin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "admin-details";
        }
        
        return "redirect:/admins";
    }
}
