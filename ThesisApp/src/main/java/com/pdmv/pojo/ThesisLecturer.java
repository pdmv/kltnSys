/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phamdominhvuong
 */
@Entity
@Table(name = "thesis_lecturer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThesisLecturer.findAll", query = "SELECT t FROM ThesisLecturer t"),
    @NamedQuery(name = "ThesisLecturer.findById", query = "SELECT t FROM ThesisLecturer t WHERE t.id = :id")})
public class ThesisLecturer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    @ManyToOne
    private Lecturer lecturerId;
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
    @ManyToOne
    private Thesis thesisId;

    public ThesisLecturer() {
    }

    public ThesisLecturer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Lecturer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Lecturer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Thesis getThesisId() {
        return thesisId;
    }

    public void setThesisId(Thesis thesisId) {
        this.thesisId = thesisId;
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
        if (!(object instanceof ThesisLecturer)) {
            return false;
        }
        ThesisLecturer other = (ThesisLecturer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pdmv.pojo.ThesisLecturer[ id=" + id + " ]";
    }
    
}
