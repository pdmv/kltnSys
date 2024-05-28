/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.services.AdminService;
import com.pdmv.services.FacultyService;
import com.pdmv.services.SchoolYearService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SchoolYearService schoolYearSrevice;
    @Autowired
    private FacultyService facultyService;
    
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
    
    @GetMapping("/school-years")
    public String getSchoolYear(Model model) {
        model.addAttribute("schoolYears", this.schoolYearSrevice.getSchoolYears());
        return "school-years";
    }
    
    @GetMapping("/school-years/add")
    public String createSchoolYearView(Model model) {
        model.addAttribute("schoolYear", new SchoolYear());
        return "add-school-year";
    }
    
    @PostMapping("/school-years")
    public String createSchoolYear(@ModelAttribute("schoolYear") @Valid SchoolYear schoolYear, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "add-school-year"; 
        }

        try {
            this.schoolYearSrevice.addOrUpdate(schoolYear); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm niên khoá.");
            return "add-school-year";
        }

        return "redirect:/admins/school-years";
    }
    
    @GetMapping("/school-years/{id}")
    public String updateSchoolYearView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("schoolYear", this.schoolYearSrevice.getSchoolYearById(id));
        return "update-school-year";
    }
    
    @PostMapping("/school-years/{id}")
    public String updateSchoolYear(@ModelAttribute("schoolYear") @Valid SchoolYear schoolYear, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "update-school-year"; 
        }

        try {
            this.schoolYearSrevice.addOrUpdate(schoolYear); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật niên khoá.");
            return "update-school-year";
        }

        return "redirect:/admins/school-years";
    }
    
    @GetMapping("/faculties")
    public String getFaculties(Model model, @RequestParam Map<String, String> params) {
        String kw = params.getOrDefault("kw", "");
        model.addAttribute("faculties", this.facultyService.getFaculties(kw));
        return "faculties";
    }
    
    @GetMapping("/faculties/add")
    public String createFacultyView(Model model) {
        Faculty faculty = new Faculty();
        model.addAttribute("faculty", faculty);
        return "add-faculty";
    }
    
    @PostMapping("/faculties")
    public String createFaculty(@ModelAttribute("faculty") @Valid Faculty faculty, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "add-faculty";
        }

        try {
            this.facultyService.addOrUpdate(faculty); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm khoa đào tạo.");
            return "add-faculty";
        }

        return "redirect:/admins/faculties";
    }
    
    @GetMapping("/faculties/{id}")
    public String updateFacultyView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("faculty", this.facultyService.getFacultyById(id));
        return "update-faculty";
    }
    
    @PostMapping("/faculties/{id}")
    public String updateFaculty(@ModelAttribute("schoolYear") @Valid Faculty faculty, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "update-faculty"; 
        }

        try {
            System.out.println(faculty.getName());
            this.facultyService.addOrUpdate(faculty); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật khoa đào tạo.");
            return "update-faculty";
        }

        return "redirect:/admins/faculties";
    }
}
