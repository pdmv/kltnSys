/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.services.AdminService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private BCryptPasswordEncoder passwordEncoder;
    
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
        return "add-admin";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("admin") @Valid Admin admin, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            return "add-admin";
        }

        try {
            this.adminService.addOrUpdate(admin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "add-admin";
        }
        
        return "redirect:/admins";
    }
    
    @GetMapping("/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        Admin admin = this.adminService.getAdminById(id);
        admin.getAccountId().setPassword(null);
        model.addAttribute(admin);
        return "update-admin";
    }
    
    @PostMapping("/{id}")
    public String update(@ModelAttribute("admin") @Valid Admin admin, 
                          BindingResult result,
                          HttpServletRequest request,
                          Model model) {
        if (result.hasErrors()) {
            System.err.println(result.getAllErrors());
            return "update-admin";
        }

        try {
            this.adminService.addOrUpdate(admin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "update-admin";
        }

        return "redirect:/admins";
    }
}
