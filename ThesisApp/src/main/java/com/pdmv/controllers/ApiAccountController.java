/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.components.JwtService;
import com.pdmv.dto.request.ChangePasswordRequest;
import com.pdmv.dto.response.MessageResponse;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Student;
import com.pdmv.services.AccountService;
import com.pdmv.services.AdminService;
import com.pdmv.services.AffairService;
import com.pdmv.services.LecturerService;
import com.pdmv.services.StudentService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private AffairService affairService;
    @Autowired
    private AdminService adminService;
    
    
    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody Account account) {
        if (account.getUsername() == null || account.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            if (this.accountService.authAccount(account.getUsername(), account.getPassword()) == true) {
                String token = this.jwtService.generateTokenLogin(account.getUsername());

                return new ResponseEntity<>(token, HttpStatus.OK);
            }

            return new ResponseEntity<>(new MessageResponse("Tên đăng nhập hoặc mật khẩu sai!"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        Account account = this.accountService.getAccountByUsername(principal.getName());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        switch (account.getRole()) {
            case "STUDENT":
                Student student = this.studentService.getStudentByAccountId(account.getId());
                return new ResponseEntity<>(student, HttpStatus.OK); 
            case "LECTURER":
                Lecturer lecturer = this.lecturerService.getLecturerByAccountId(account.getId());
                return new ResponseEntity<>(lecturer, HttpStatus.OK); 
            case "AFFAIR":
                Affair affair = this.affairService.getAffairByAccountId(account.getId());
                return new ResponseEntity<>(affair, HttpStatus.OK); 
            case "ADMIN":
                Admin admin = this.adminService.getAdminByAccountId(account.getId());
                return new ResponseEntity<>(admin, HttpStatus.OK); 
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    @PostMapping(path = "/change-password/", produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody ChangePasswordRequest request) {
        try {
            if (!this.accountService.authAccount(principal.getName(), request.getOldPassword())) {
                return new ResponseEntity<>(new MessageResponse("Mật khẩu cũ sai!"), HttpStatus.BAD_REQUEST);
            }

            if (request.getNewPassword().isEmpty()) {
                return new ResponseEntity<>(new MessageResponse("Mật khẩu mới không được để trống!"), HttpStatus.BAD_REQUEST);
            }

            if (request.getNewPassword().equals(request.getOldPassword())) {
                return new ResponseEntity<>(new MessageResponse("Mật khẩu mới phải khác mật khẩu cũ!"), HttpStatus.BAD_REQUEST); 
            }

            this.accountService.changePassword(principal.getName(), request.getOldPassword(), request.getNewPassword()); 

            return new ResponseEntity<>(new MessageResponse("Đổi mật khẩu thành công!"), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("Có lỗi xảy ra khi đổi mật khẩu."), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}