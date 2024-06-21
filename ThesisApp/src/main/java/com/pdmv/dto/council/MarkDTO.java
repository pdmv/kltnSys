/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Lecturer;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class MarkDTO {
    private Council councilId;
    private int thesisId;
    private Lecturer lecturerId;
    private Set<ScoreDTO> scores;
}
