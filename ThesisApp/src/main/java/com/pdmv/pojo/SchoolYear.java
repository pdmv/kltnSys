/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author phamdominhvuong
 */
@Entity
@Table(name = "school_year")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SchoolYear.findAll", query = "SELECT s FROM SchoolYear s"),
    @NamedQuery(name = "SchoolYear.findById", query = "SELECT s FROM SchoolYear s WHERE s.id = :id"),
    @NamedQuery(name = "SchoolYear.findByStartYear", query = "SELECT s FROM SchoolYear s WHERE s.startYear = :startYear"),
    @NamedQuery(name = "SchoolYear.findByEndYear", query = "SELECT s FROM SchoolYear s WHERE s.endYear = :endYear"),
    @NamedQuery(name = "SchoolYear.findByCreatedDate", query = "SELECT s FROM SchoolYear s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "SchoolYear.findByUpdatedDate", query = "SELECT s FROM SchoolYear s WHERE s.updatedDate = :updatedDate"),
    @NamedQuery(name = "SchoolYear.findByActive", query = "SELECT s FROM SchoolYear s WHERE s.active = :active")})
public class SchoolYear implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_year")
    @Temporal(TemporalType.DATE)
    private Date startYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_year")
    @Temporal(TemporalType.DATE)
    private Date endYear;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "schoolYearId")
    private Set<Council> councilSet;
    @OneToMany(mappedBy = "schoolYearId")
    private Set<Thesis> thesisSet;

    public SchoolYear() {
    }

    public SchoolYear(Integer id) {
        this.id = id;
    }

    public SchoolYear(Integer id, Date startYear, Date endYear) {
        this.id = id;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartYear() {
        return startYear;
    }

    public void setStartYear(Date startYear) {
        this.startYear = startYear;
    }

    public Date getEndYear() {
        return endYear;
    }

    public void setEndYear(Date endYear) {
        this.endYear = endYear;
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

    @XmlTransient
    public Set<Council> getCouncilSet() {
        return councilSet;
    }

    public void setCouncilSet(Set<Council> councilSet) {
        this.councilSet = councilSet;
    }

    @XmlTransient
    public Set<Thesis> getThesisSet() {
        return thesisSet;
    }

    public void setThesisSet(Set<Thesis> thesisSet) {
        this.thesisSet = thesisSet;
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
        if (!(object instanceof SchoolYear)) {
            return false;
        }
        SchoolYear other = (SchoolYear) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pdmv.pojo.SchoolYear[ id=" + id + " ]";
    }
    
}
