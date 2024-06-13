/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdmv.pojo.Affair;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ThesisAffairDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    
    public static ThesisAffairDTO toThesisAffairDTO(Affair affair) {
        ThesisAffairDTO dto = new ThesisAffairDTO();
        dto.setId(affair.getId());
        dto.setFirstName(affair.getFirstName());
        dto.setLastName(affair.getLastName());
        dto.setEmail(affair.getEmail());
        return dto;
    }
    
    @JsonIgnore
    public String getFullname() {
        return this.lastName + ' ' + this.firstName;
    }
}
