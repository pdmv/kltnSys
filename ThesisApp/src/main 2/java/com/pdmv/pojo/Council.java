/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdmv.dto.FacultyDTO;
import com.pdmv.dto.SchoolYearDTO;
import com.pdmv.dto.council.CouncilCriterionDTO;
import com.pdmv.dto.council.CouncilLecturerDTO;
import com.pdmv.dto.council.CouncilThesisDTO;
import com.pdmv.dto.thesis.ThesisAffairDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author phamdominhvuong
 */
@Entity
@Table(name = "council")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Council.findAll", query = "SELECT c FROM Council c"),
    @NamedQuery(name = "Council.findById", query = "SELECT c FROM Council c WHERE c.id = :id"),
    @NamedQuery(name = "Council.findByName", query = "SELECT c FROM Council c WHERE c.name = :name"),
    @NamedQuery(name = "Council.findByMeetingDate", query = "SELECT c FROM Council c WHERE c.meetingDate = :meetingDate"),
    @NamedQuery(name = "Council.findByStatus", query = "SELECT c FROM Council c WHERE c.status = :status"),
    @NamedQuery(name = "Council.findByCreatedDate", query = "SELECT c FROM Council c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Council.findByUpdatedDate", query = "SELECT c FROM Council c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "Council.findByActive", query = "SELECT c FROM Council c WHERE c.active = :active")})
public class Council implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Faculty facultyId;
    @Column(name = "meeting_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date meetingDate;
    @Size(max = 7)
    @Column(name = "status")
    private String status;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "affair_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Affair affairId;
    @OneToMany(mappedBy = "councilId", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CouncilLecturer> councilLecturerSet;
    @OneToMany(mappedBy = "councilId", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CouncilCriterion> councilCriterionSet;
    @OneToMany(mappedBy = "councilId", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CouncilThesis> councilThesisSet;
    @OneToMany(mappedBy = "councilId")
    @JsonIgnore
    private Set<Score> scoreSet;
    @JoinColumn(name = "school_year_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private SchoolYear schoolYearId;

    public Council() {
    }

    public Council(Integer id) {
        this.id = id;
    }

    public Council(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDatetime) {
        this.meetingDate = meetingDatetime;
    }
    
    public Faculty getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Faculty facultyId) {
        this.facultyId = facultyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Affair getAffairId() {
        return affairId;
    }

    public void setAffairId(Affair affairId) {
        this.affairId = affairId;
    }

    @XmlTransient
    public Set<CouncilLecturer> getCouncilLecturerSet() {
        return councilLecturerSet;
    }

    public void setCouncilLecturerSet(Set<CouncilLecturer> councilLecturerSet) {
        this.councilLecturerSet = councilLecturerSet;
    }

    @XmlTransient
    public Set<CouncilCriterion> getCouncilCriterionSet() {
        return councilCriterionSet;
    }

    public void setCouncilCriterionSet(Set<CouncilCriterion> councilCriterionSet) {
        this.councilCriterionSet = councilCriterionSet;
    }
    
    @XmlTransient
    public Set<CouncilThesis> getCouncilThesisSet() {
        return councilThesisSet;
    }

    public void setCouncilThesisSet(Set<CouncilThesis> councilThesisSet) {
        this.councilThesisSet = councilThesisSet;
    }

    @XmlTransient
    public Set<Score> getScoreSet() {
        return scoreSet;
    }

    public void setScoreSet(Set<Score> scoreSet) {
        this.scoreSet = scoreSet;
    }

    public SchoolYear getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(SchoolYear schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Council)) {
            return false;
        }
        Council other = (Council) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @JsonProperty("schoolYearId") 
    public SchoolYearDTO getSchoolYearInfo() {
        if (schoolYearId != null) {
            SchoolYearDTO dto = new SchoolYearDTO();
            
            dto.setId(schoolYearId.getId());
            dto.setStartYear(schoolYearId.getStartYear());
            dto.setEndYear(schoolYearId.getEndYear());
            
            return dto;
        }
        return null;
    }
    
    @JsonProperty("affair") 
    public ThesisAffairDTO getAffairInfo() {
        if (affairId != null) {
            ThesisAffairDTO dto = new ThesisAffairDTO();
            
            dto.setId(affairId.getId());
            dto.setFirstName(affairId.getFirstName());
            dto.setLastName(affairId.getLastName());
            dto.setEmail(affairId.getEmail());
            
            return dto;
        }
        return null;
    }
    
    @JsonProperty("faculty") 
    public FacultyDTO getFacultyInfo() {
        if (facultyId != null) {
            FacultyDTO facultyDTO = new FacultyDTO();
            
            facultyDTO.setId(facultyId.getId());
            facultyDTO.setName(facultyId.getName());
            
            return facultyDTO;
        }
        return null;
    }
    
    @JsonProperty("councilLecturerSet") 
    public Set<CouncilLecturerDTO> getCouncilLecturerSetInfo() {
        return this.councilLecturerSet.stream().map(CouncilLecturerDTO::toCouncilLecturerDTO)
                .collect(Collectors.toSet());
    }
    
    @JsonProperty("councilCriterionSet") 
    public Set<CouncilCriterionDTO> getCouncilCriterionSetInfo() {
        return this.councilCriterionSet.stream().map(CouncilCriterionDTO::toCouncilCriterionDTO)
                .collect(Collectors.toSet());
    }
    
    @JsonProperty("councilThesisSet") 
    public Set<CouncilThesisDTO> getCouncilThesisSetInfo() {
        return this.councilThesisSet.stream().map(CouncilThesisDTO::toCouncilThesisDTO)
                .collect(Collectors.toSet());
    }
    
    @Override
    public String toString() {
        return "com.pdmv.pojo.Council[ id=" + id + " ]";
    }
    
    @PrePersist
    public void prePersist() {
        createdDate = new Date();
        updatedDate = createdDate;
        active = true;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = new Date();
    }
}
