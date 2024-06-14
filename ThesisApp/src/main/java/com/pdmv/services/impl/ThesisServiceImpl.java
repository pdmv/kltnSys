/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.CreateThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import com.pdmv.dto.ThesisLecturerDTO;
import com.pdmv.dto.ThesisStudentDTO;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.repositories.AccountRepository;
import com.pdmv.repositories.AffairRepository;
import com.pdmv.repositories.ThesisRepository;
import com.pdmv.services.ThesisService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author phamdominhvuong
 */
@Service
@PropertySource("classpath:theses.properties")
public class ThesisServiceImpl implements ThesisService {
    @Autowired
    private ThesisRepository thesisRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AffairRepository affairRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private Environment env;
    @Autowired
    private EmailService emailService;
    
    @Override
    public ThesisDetailsDTO addOrUpdate(CreateThesisDTO thesis) {
        if (thesis.getThesisStudentSet().size() > Integer.parseInt(env.getProperty("thesis.maxStudents"))) {
            throw new IllegalArgumentException("Số lượng sinh viên vượt quá giới hạn cho phép.");
        }
        
        if (thesis.getThesisLecturerSet().size() > Integer.parseInt(env.getProperty("thesis.maxLecturers"))) {
            throw new IllegalArgumentException("Số lượng giảng viên hướng dẫn vượt quá giới hạn cho phép.");
        }
        
        if (thesis.getAffairId() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Account a = this.accountRepo.getAccountByUsername(authentication.getName());
            Affair affair = this.affairRepo.getAffairByAccountId(a.getId());
            
            thesis.setAffairId(affair.getId());
        }
        
        int id = this.thesisRepo.addOrUpdate(thesis);
        ThesisDetailsDTO newThesis = this.thesisRepo.getThesisById(id);
        
        if (thesis.getId() == null) {
            this.sendNotificationMail(newThesis);
        }
        
        return newThesis;
    }

    @Override
    public ThesisDetailsDTO getThesisById(int id) {
        return this.thesisRepo.getThesisById(id);
    }

    @Override
    public List<ThesisDTO> getLists(Map<String, String> params) {
        return this.thesisRepo.getLists(params);
    }

    @Override
    public void submitReportFile(int id, MultipartFile file) {
        ThesisDetailsDTO thesis = this.thesisRepo.getThesisById(id);
         
        if (thesis == null) {
            throw new IllegalArgumentException("Không tìm thấy khoá luận!");
        }
        
        if (!thesis.getStatus().equals(this.env.getProperty("thesis.status.in_progress")) && !thesis.getStatus().equals(this.env.getProperty("thesis.status.submitted"))) {
            throw new IllegalArgumentException("Không thể nộp bài lúc này!");
        }
        
        if (file != null && !file.isEmpty()) {
            try {
                if (thesis.getReportFile() != null && !thesis.getReportFile().isEmpty()) {
                    String oldFile = extractPublicId(thesis.getReportFile());
                    try {
                        ApiResponse apiResponse = this.cloudinary.api().deleteResources(Arrays.asList(oldFile),
                                ObjectUtils.asMap("type", "upload", "resource_type", "raw"));
                        
                        System.out.println(apiResponse);
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    } catch (Exception ex) {
                        Logger.getLogger(ThesisServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                String originalFilename = file.getOriginalFilename();
                String timestamp = String.valueOf(System.currentTimeMillis());
                String publicId = timestamp + "_" + originalFilename;

                Map<String, String> params = ObjectUtils.asMap(
                    "folder", "",
                    "resource_type", "auto",
                    "public_id", publicId 
                );
                
                Map res = this.cloudinary.uploader().upload(file.getBytes(), params);
                this.thesisRepo.submitReportFile(id, res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String extractPublicId(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        
        return url.substring(lastSlashIndex + 1);
    }
    
    private void sendNotificationMail(ThesisDetailsDTO thesis) {
        this.emailService.sendThesisNotificationEmail(thesis, thesis.getCriticalLecturerId().getEmail(), thesis.getCriticalLecturerId().getFullname(), "Giảng viên phản biện");
        
        for (ThesisLecturerDTO l : thesis.getThesisLecturerSet()) {
            this.emailService.sendThesisNotificationEmail(thesis, l.getEmail(), l.getFullname(), "Giảng viên hướng dẫn");
        }
        
        for (ThesisStudentDTO stu : thesis.getThesisStudentSet()) {
            this.emailService.sendThesisNotificationEmail(thesis, stu.getEmail(), stu.getFullname(), "Sinh viên tham gia");
        }
    }
}
