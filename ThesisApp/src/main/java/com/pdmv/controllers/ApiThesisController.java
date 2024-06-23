/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.report.Report;
import com.pdmv.dto.thesis.ThesisDTO;
import com.pdmv.dto.thesis.CreateThesisDTO;
import com.pdmv.dto.thesis.ThesisDetailsDTO;
import com.pdmv.dto.thesis.ThesisStudentDTO;
import com.pdmv.dto.response.MessageResponse;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Student;
import com.pdmv.services.AccountService;
import com.pdmv.services.AffairService;
import com.pdmv.services.LecturerService;
import com.pdmv.services.StudentService;
import com.pdmv.services.ThesisService;
import com.pdmv.services.impl.PdfService;
import com.pdmv.services.impl.ReportService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private AffairService affairService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private PdfService pdfService;

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
    public ResponseEntity<?> retrieve(Principal principal, @PathVariable(value = "id") Integer id) {
        try {
            ThesisDetailsDTO thesisDTO = this.thesisService.getThesisById(id);

            Account acc = this.accountService.getAccountByUsername(principal.getName());

            if (acc == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            switch (acc.getRole()) {
                case "AFFAIR":
                    Affair affair = this.affairService.getAffairByAccountId(acc.getId());
                    if (!Objects.equals(affair.getFacultyId().getId(), thesisDTO.getFacultyId().getId())) {
                        return new ResponseEntity<>(new MessageResponse("Bạn không có quyền truy cập khoá luận này!"), HttpStatus.FORBIDDEN);
                    }
                    break;
                case "LECTURER":
                    Lecturer lecturer = this.lecturerService.getLecturerByAccountId(acc.getId());
                    if (!Objects.equals(lecturer.getFacultyId().getId(), thesisDTO.getFacultyId().getId())) {
                        return new ResponseEntity<>(new MessageResponse("Bạn không có quyền truy cập khoá luận này!"), HttpStatus.FORBIDDEN);
                    }
                    break;
                case "STUDENT":
                    Student stu = this.studentService.getStudentByAccountId(acc.getId());
                    boolean isOwner = false;
                    for (ThesisStudentDTO dto : thesisDTO.getThesisStudentSet()) {
                        if (Objects.equals(stu.getId(), dto.getStudentId())) {
                            isOwner = true;
                        }
                    }
                    if (!isOwner) {
                        return new ResponseEntity<>(new MessageResponse("Bạn không có quyền truy cập khoá luận này!"), HttpStatus.FORBIDDEN);
                    }
                    break;
            }

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
                if (Objects.equals(dto.getStudentId(), student.getId())) {
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

    @GetMapping(path = "/thesis/{id}/report/")
    public ResponseEntity<?> generateReport(@PathVariable(value = "id") int thesisId) {
        try {
            Report report = this.reportService.report(thesisId);
            byte[] pdfBytes = pdfService.generateThesisScoreReport(report);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "thesis_score_report.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
