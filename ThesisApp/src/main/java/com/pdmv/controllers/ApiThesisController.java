/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.CreateThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import com.pdmv.dto.ThesisStudentDTO;
import com.pdmv.dto.response.MessageResponse;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Student;
import com.pdmv.services.AccountService;
import com.pdmv.services.StudentService;
import com.pdmv.services.ThesisService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiThesisController {
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AccountService accountService;
    
    @PostMapping(path = "/thesis/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> create(@RequestBody CreateThesisDTO thesisDTO) {
        
        try {
            ThesisDetailsDTO thesis = this.thesisService.addOrUpdate(thesisDTO);
            return new ResponseEntity<>(thesis, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
        
    }
    
    @GetMapping(path = "/thesis/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> retrieve(@PathVariable(value = "id") Integer id) {
        try {
            ThesisDetailsDTO thesisDTO = this.thesisService.getThesisById(id);

            if (thesisDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(thesisDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path = "/thesis/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<ThesisDTO>> list(@RequestParam Map<String, String> params) {
        List<ThesisDTO> list = this.thesisService.getLists(params);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PostMapping(path = "/thesis/{id}/submit-report-file/", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.APPLICATION_JSON_VALUE
    }, produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> submitReportFile(Principal principal, @PathVariable(value = "id") Integer id, @RequestPart MultipartFile[] file) {
        try {
            Account account = this.accountService.getAccountByUsername(principal.getName());

            if (!account.getRole().equals("STUDENT")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            
            Student student = this.studentService.getStudentByAccountId(account.getId());
            boolean isOwner = false;
            
            ThesisDetailsDTO thesis = this.thesisService.getThesisById(id);
            
            if (thesis == null) {
                throw new IllegalArgumentException("Không tìm thấy khoá luận!");
            }
            
            for (ThesisStudentDTO dto : thesis.getThesisStudentSet()) {
                if (dto.getStudentId() == student.getId()) {
                    isOwner = true;
                    break;
                }
            }
            
            if (!isOwner) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            
            this.thesisService.submitReportFile(id, file[0]);
            return new ResponseEntity<>(this.thesisService.getThesisById(id), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
