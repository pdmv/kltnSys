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
@Table(name = "council_criterion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CouncilCriterion.findAll", query = "SELECT c FROM CouncilCriterion c"),
    @NamedQuery(name = "CouncilCriterion.findById", query = "SELECT c FROM CouncilCriterion c WHERE c.id = :id"),
    @NamedQuery(name = "CouncilCriterion.findByWeight", query = "SELECT c FROM CouncilCriterion c WHERE c.weight = :weight")})
public class CouncilCriterion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "council_id", referencedColumnName = "id")
    @ManyToOne
    private Council councilId;
    @JoinColumn(name = "criterion_id", referencedColumnName = "id")
    @ManyToOne
    private Criterion criterionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Float weight;

    public CouncilCriterion() {
    }

    public CouncilCriterion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Council getCouncilId() {
        return councilId;
    }

    public void setCouncilId(Council councilId) {
        this.councilId = councilId;
    }

    public Criterion getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(Criterion criterionId) {
        this.criterionId = criterionId;
    }
    
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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
        if (!(object instanceof CouncilCriterion)) {
            return false;
        }
        CouncilCriterion other = (CouncilCriterion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pdmv.pojo.CouncilCriterion[ id=" + id + " ]";
    }
    
}
