/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto.council;

import com.pdmv.pojo.Score;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phamdominhvuong
 */
@Getter
@Setter
public class ScoreDTO {
    private int id; // criterionId
    private float score;
    
    public static ScoreDTO toScoreDTO(Score score) {
    ScoreDTO dto = new ScoreDTO();
    dto.setId(score.getCriterionId().getId()); 
    dto.setScore(score.getScore());
    return dto;
}
}
