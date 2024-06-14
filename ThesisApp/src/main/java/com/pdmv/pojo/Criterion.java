/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "criterion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Criterion.findAll", query = "SELECT c FROM Criterion c"),
    @NamedQuery(name = "Criterion.findById", query = "SELECT c FROM Criterion c WHERE c.id = :id"),
    @NamedQuery(name = "Criterion.findByName", query = "SELECT c FROM Criterion c WHERE c.name = :name"),
    @NamedQuery(name = "Criterion.findByCreatedDate", query = "SELECT c FROM Criterion c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Criterion.findByUpdatedDate", query = "SELECT c FROM Criterion c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "Criterion.findByActive", query = "SELECT c FROM Criterion c WHERE c.active = :active")})
public class Criterion implements Serializable {

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
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
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
    private Affair affairId;
    @OneToMany(mappedBy = "criterionId")
    private Set<CouncilCriterion> councilCriterionSet;
    @OneToMany(mappedBy = "criterionId")
    private Set<Score> scoreSet;

    public Criterion() {
    }

    public Criterion(Integer id) {
        this.id = id;
    }

    public Criterion(Integer id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Set<CouncilCriterion> getCouncilCriterionSet() {
        return councilCriterionSet;
    }

    public void setCouncilCriterionSet(Set<CouncilCriterion> councilCriterionSet) {
        this.councilCriterionSet = councilCriterionSet;
    }

    @XmlTransient
    public Set<Score> getScoreSet() {
        return scoreSet;
    }

    public void setScoreSet(Set<Score> scoreSet) {
        this.scoreSet = scoreSet;
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
        if (!(object instanceof Criterion)) {
            return false;
        }
        Criterion other = (Criterion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pdmv.pojo.Criterion[ id=" + id + " ]";
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
