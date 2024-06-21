/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.response.MessageResponse;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Criterion;
import com.pdmv.services.AccountService;
import com.pdmv.services.AffairService;
import com.pdmv.services.CriterionService;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api/criterion")
@CrossOrigin
public class ApiCriterionController {
    @Autowired
    private CriterionService criterionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AffairService affairService;
    
    @PostMapping(path = "/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> create(Principal principal, @RequestBody Criterion criterion) {
        Account account = this.accountService.getAccountByUsername(principal.getName());
        
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        if (!account.getRole().equals("AFFAIR")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Affair affair = this.affairService.getAffairByAccountId(account.getId());
        criterion.setAffairId(affair);
        
        try {
            Criterion result = this.criterionService.getCriterionById(this.criterionService.addOrUpdate(criterion));
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path = "/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> retrieve(Principal principal, @PathVariable(value = "id") Integer id) {
        try {
             return new ResponseEntity<>(this.criterionService.getCriterionById(id), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(path = "/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> update(Principal principal, @PathVariable(value = "id") Integer id, @RequestBody Criterion criterion) {
        Account account = this.accountService.getAccountByUsername(principal.getName());
        
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        if (!account.getRole().equals("AFFAIR") && !account.getRole().equals("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        if (criterion.getId() == null) {
            criterion.setId(id);
        }
        
        try {
            Criterion c = this.criterionService.getCriterionById(id);
            
            if (account.getRole().equals("AFFAIR")) {
                Affair affair = this.affairService.getAffairByAccountId(account.getId());
                if (!Objects.equals(c.getFacultyId().getId(), affair.getFacultyId().getId())) {
                    return new ResponseEntity<>(new MessageResponse("Bạn không được cập nhật tiêu chí của khoa khác!"), HttpStatus.FORBIDDEN);
                }
            }
            
            if (criterion.getName() == null || criterion.getName().isEmpty()) {
                return new ResponseEntity<>(new MessageResponse("Tên tiêu chí không được null hoặc để trống!"), HttpStatus.BAD_REQUEST);
            }
            
            criterion.setAffairId(c.getAffairId());
            return new ResponseEntity<>(this.criterionService.getCriterionById(this.criterionService.addOrUpdate(criterion)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path = "/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.criterionService.list(params), HttpStatus.OK);
    }
}
