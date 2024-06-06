/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.cloudinary.Cloudinary;
import com.pdmv.dto.ThesisDTO;
import com.pdmv.dto.CreateThesisDTO;
import com.pdmv.dto.ThesisDetailsDTO;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.repositories.AccountRepository;
import com.pdmv.repositories.AffairRepository;
import com.pdmv.repositories.ThesisRepository;
import com.pdmv.services.ThesisService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class ThesisServiceImpl implements ThesisService {
    @Autowired
    private ThesisRepository thesisRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AffairRepository affairRepo;
    @Autowired
    private Cloudinary cloudinary;
    
//    @Override
//    public void addOrUpdate(Thesis thesis) {
//        if (thesis.getFile() != null && !thesis.getFile().isEmpty()) {
//            try {
//                Map res = this.cloudinary.uploader().upload(thesis.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                
//                if (thesis.getId() != null) {
//                    Thesis exist = this.thesisRepo.getThesisById(thesis.getId());
//                    
//                    if (exist.getReportFile()!= null && !exist.getReportFile().isEmpty()) {
//                        String publicId = extractPublicId(thesis.getReportFile());
//                        try {
//                            ApiResponse apiResponse = this.cloudinary.api().deleteResources(Arrays.asList(publicId),
//                                ObjectUtils.asMap("type", "upload", "resource_type", "raw"));
//                            System.out.println(apiResponse);
//                        } catch (IOException exception) {
//                            System.out.println(exception.getMessage());
//                        } catch (Exception ex) {
//                            Logger.getLogger(ThesisServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//                
//                thesis.setReportFile(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (thesis.getId() != null) {
//            thesis.setReportFile(this.thesisRepo.getThesisById(thesis.getId()).getReportFile());
//        }
//        
//        this.thesisRepo.addOrUpdate(thesis);
//    }
//
//    @Override
//    public Thesis getThesisById(int id) {
//        return this.thesisRepo.getThesisById(id);
//    }
//
//    @Override
//    public List<Thesis> getThesises(Map<String, String> params) {
//        return this.thesisRepo.getThesises(params);
//    }
    
    

    @Override
    public void addOrUpdate(CreateThesisDTO thesis) {
        if (thesis.getAffairId() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Account a = this.accountRepo.getAccountByUsername(authentication.getName());
            Affair affair = this.affairRepo.getAffairByAccountId(a.getId());
            
            thesis.setAffairId(affair.getId());
        }
        
        this.thesisRepo.addOrUpdate(thesis);
    }

    @Override
    public ThesisDetailsDTO getThesisById(int id) {
        return this.thesisRepo.getThesisById(id);
    }

    @Override
    public List<ThesisDTO> getLists(Map<String, String> params) {
        return this.thesisRepo.getLists(params);
    }
    
    private String extractPublicId(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        
        return url.substring(lastSlashIndex + 1);
    }
}
