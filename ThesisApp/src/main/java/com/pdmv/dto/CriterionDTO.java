/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.dto;

import com.pdmv.pojo.Criterion;
import java.util.Date;
/**
 *
 * @author tid83
 */
public class CriterionDTO {
    private Integer id;
    private String name;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private Boolean active;
    private Integer affairId;

    public CriterionDTO() {}

    public CriterionDTO(Criterion criterion) {
        this.id = criterion.getId();
        this.name = criterion.getName();
        this.description = criterion.getDescription();
        this.createdDate = criterion.getCreatedDate();
        this.updatedDate = criterion.getUpdatedDate();
        this.active = criterion.getActive();
        this.affairId = criterion.getAffairId().getId();
    }

    public static CriterionDTO toCriterionDTO(Criterion criterion) {
        return new CriterionDTO(criterion);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the affairId
     */
    public Integer getAffairId() {
        return affairId;
    }

    /**
     * @param affairId the affairId to set
     */
    public void setAffairId(Integer affairId) {
        this.affairId = affairId;
    }

    
}
