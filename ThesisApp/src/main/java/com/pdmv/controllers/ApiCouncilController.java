/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.dto.council.CreateCouncilDTO;
import com.pdmv.dto.council.MarkDTO;
import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.dto.response.MessageResponse;
import com.pdmv.pojo.Account;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Council;
import com.pdmv.pojo.Lecturer;
import com.pdmv.services.AccountService;
import com.pdmv.services.AffairService;
import com.pdmv.services.CouncilService;
import com.pdmv.services.LecturerService;
import java.security.Principal;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phamdominhvuong
 */
@RestController
@RequestMapping("api/council")
@CrossOrigin
public class ApiCouncilController {

    @Autowired
    private CouncilService councilService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AffairService affairService;
    @Autowired
    private LecturerService lecturerService;

    @PostMapping(path = "/", consumes = {
        MediaType.APPLICATION_JSON_VALUE
    }, produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> create(Principal principal, @RequestBody CreateCouncilDTO dto) {
        Account account = this.accountService.getAccountByUsername(principal.getName());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!account.getRole().equals("AFFAIR")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Affair affair = this.affairService.getAffairByAccountId(account.getId());
        dto.setAffairId(affair);
        dto.setFacultyId(affair.getFacultyId());

        try {
            Integer id = this.councilService.addCouncil(dto);
            return new ResponseEntity<>(this.councilService.getCouncilById(id), HttpStatus.OK);
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
        List<SimpleCouncilDTO> list = this.councilService.getLists(params);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> retrieve(@PathVariable(value = "id") Integer id) {
        try {
            return new ResponseEntity<>(this.councilService.getCouncilById(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/criterions/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> getCriterions(@PathVariable(value = "id") Integer id) {
        try {
            return new ResponseEntity<>(this.councilService.getCouncilById(id).getCouncilCriterionSetInfo(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{id}/mark/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> mark(Principal principal, @PathVariable(value = "id") Integer id, @RequestBody MarkDTO mark) {
        Account account = this.accountService.getAccountByUsername(principal.getName());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!account.getRole().equals("LECTURER")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Lecturer lecturer = this.lecturerService.getLecturerByAccountId(account.getId());
        mark.setLecturerId(lecturer);
        mark.setCouncilId(this.councilService.getCouncilById(id));

        try {
            this.councilService.mark(mark);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/marks/", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> getMarks(@RequestParam Map<String, String> params) {
        try {
            int councilId = Integer.parseInt(params.get("councilId"));
            int thesisId = Integer.parseInt(params.get("thesisId"));
            int lecturerId = Integer.parseInt(params.get("lecturerId"));

            MarkDTO markDTO = this.councilService.getMarks(councilId, thesisId, lecturerId);

            if (markDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(markDTO, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid parameter format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/block/")
    public ResponseEntity<?> blockCouncil(Principal principal, @PathVariable("id") int councilId) {
        Account account = this.accountService.getAccountByUsername(principal.getName());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!account.getRole().equals("AFFAIR")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            // Kiểm tra khoa của giáo vụ
            Affair affair = this.affairService.getAffairByAccountId(account.getId());
            Council council = this.councilService.getCouncilById(councilId);
            if (!Objects.equals(affair.getFacultyId().getId(), council.getFacultyId().getId())) {
                return new ResponseEntity<>(new MessageResponse("Bạn không có quyền khóa hội đồng này."), HttpStatus.BAD_REQUEST);
            }

            this.councilService.blockCouncil(councilId);
            return new ResponseEntity<>(new MessageResponse("Khóa hội đồng thành công."), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/unblock/")
    public ResponseEntity<?> unblockCouncil(Principal principal, @PathVariable("id") int councilId) {
        Account account = this.accountService.getAccountByUsername(principal.getName());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!account.getRole().equals("AFFAIR")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            // Kiểm tra khoa của giáo vụ
            Affair affair = this.affairService.getAffairByAccountId(account.getId());
            Council council = this.councilService.getCouncilById(councilId);
            if (!Objects.equals(affair.getFacultyId().getId(), council.getFacultyId().getId())) {
                return new ResponseEntity<>(new MessageResponse("Bạn không có quyền mở khóa hội đồng này."), HttpStatus.BAD_REQUEST);
            }

            this.councilService.unblockCouncil(councilId);
            return new ResponseEntity<>(new MessageResponse("Mở khóa hội đồng thành công."), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse("Lỗi hệ thống."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
