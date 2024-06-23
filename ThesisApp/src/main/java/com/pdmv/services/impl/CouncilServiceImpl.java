/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.dto.council.CreateCouncilDTO;
import com.pdmv.dto.council.MarkDTO;
import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.dto.report.CriterionScore;
import com.pdmv.dto.thesis.ThesisDetailsDTO;
import com.pdmv.dto.thesis.ThesisStudentDTO;
import com.pdmv.pojo.Council;
import com.pdmv.pojo.CouncilLecturer;
import com.pdmv.pojo.CouncilThesis;
import com.pdmv.pojo.Lecturer;
import com.pdmv.repositories.CouncilRepository;
import com.pdmv.services.CouncilService;
import com.pdmv.services.ThesisService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class CouncilServiceImpl implements CouncilService {
    @Autowired
    private CouncilRepository councilRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ThesisService thesisService;

    @Override
    public Integer addCouncil(CreateCouncilDTO dto) {
        int id = this.councilRepo.addCouncil(dto);
        Council c = this.councilRepo.getCouncilById(id);
        
        for (CouncilLecturer councilLecturer : c.getCouncilLecturerSet()) {
            Lecturer l = councilLecturer.getLecturerId();
            String recipientEmail = l.getEmail(); 
            String recipientName = l.getLastName() + " " + l.getFirstName();
            String role = this.getRole(councilLecturer.getPosition());
            
            emailService.sendCouncilNotificationEmail(c, recipientEmail, recipientName, role);
        }
        
        return id;
    }

    @Override
    public List<SimpleCouncilDTO> getLists(Map<String, String> params) {
        return this.councilRepo.getLists(params);
    }

    @Override
    public Council getCouncilById(int id) {
        return this.councilRepo.getCouncilById(id);
    }

    @Override
    public void mark(MarkDTO mark) {
        this.councilRepo.mark(mark);
    }

    @Override
    public MarkDTO getMarks(int councilId, int thesisId, int lecturerId) {
        return this.councilRepo.getMarks(councilId, thesisId, lecturerId);
    }

    @Override
    public void blockCouncil(int councilId) {
        this.councilRepo.blockCouncil(councilId);
        
        Council c = this.councilRepo.getCouncilById(councilId);
        
        for (CouncilThesis councilThesis : c.getCouncilThesisSet()) {
            ThesisDetailsDTO dto = this.thesisService.getThesisById(councilThesis.getThesisId().getId());
            this.sendAvgScoreNotificationMail(dto);
        }
    }

    @Override
    public void unblockCouncil(int councilId) {
        this.councilRepo.unblockCouncil(councilId);
    }
    
    private String getRole(String role) {
        switch (role) {
            case "president":
                return "Chủ tịch";
            case "critical":
                return "Phản biện";
            case "secretary":
                return "Thư ký";
            default:
                return "Thành viên";
        }
    }
    
    private void sendAvgScoreNotificationMail(ThesisDetailsDTO thesis) {
        for (ThesisStudentDTO stu : thesis.getThesisStudentSet()) {
            this.emailService.sendAvgScoreNotificationEmail(thesis, stu.getEmail(), stu.getFullname());
        }
    }

    @Override
    public List<CriterionScore> getCriterionScoresByThesisId(int thesisId) {
        return this.councilRepo.getCriterionScoresByThesisId(thesisId);
    }
}