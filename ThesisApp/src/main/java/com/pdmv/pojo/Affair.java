/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdmv.dto.AccountDTO;
import com.pdmv.dto.FacultyDTO;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author phamdominhvuong
 */
@Entity
@Table(name = "affair")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Affair.findAll", query = "SELECT a FROM Affair a"),
    @NamedQuery(name = "Affair.findById", query = "SELECT a FROM Affair a WHERE a.id = :id"),
    @NamedQuery(name = "Affair.findByLastName", query = "SELECT a FROM Affair a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Affair.findByFirstName", query = "SELECT a FROM Affair a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Affair.findByGender", query = "SELECT a FROM Affair a WHERE a.gender = :gender"),
    @NamedQuery(name = "Affair.findByEmail", query = "SELECT a FROM Affair a WHERE a.email = :email"),
    @NamedQuery(name = "Affair.findByDob", query = "SELECT a FROM Affair a WHERE a.dob = :dob"),
    @NamedQuery(name = "Affair.findByCreatedDate", query = "SELECT a FROM Affair a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Affair.findByUpdatedDate", query = "SELECT a FROM Affair a WHERE a.updatedDate = :updatedDate"),
    @NamedQuery(name = "Affair.findByActive", query = "SELECT a FROM Affair a WHERE a.active = :active")})
public class Affair implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 6)
    @Column(name = "gender")
    private String gender;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @Lob
    @Size(max = 65535)
    @Column(name = "address")
    private String address;
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
    @OneToMany(mappedBy = "affairId")
    @JsonIgnore
    private Set<Criterion> criterionSet;
    @OneToMany(mappedBy = "affairId")
    @JsonIgnore
    private Set<Thesis> thesisSet;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @OneToOne
    @JsonIgnore
    private Account accountId;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Faculty facultyId;

    public Affair() {
    }

    public Affair(Integer id) {
        this.id = id;
    }

    public Affair(Integer id, String lastName, String firstName, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public Set<Criterion> getCriterionSet() {
        return criterionSet;
    }

    public void setCriterionSet(Set<Criterion> criterionSet) {
        this.criterionSet = criterionSet;
    }

    @XmlTransient
    public Set<Thesis> getThesisSet() {
        return thesisSet;
    }

    public void setThesisSet(Set<Thesis> thesisSet) {
        this.thesisSet = thesisSet;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Faculty facultyId) {
        this.facultyId = facultyId;
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
        if (!(object instanceof Affair)) {
            return false;
        }
        Affair other = (Affair) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pdmv.pojo.Affair[ id=" + id + " ]";
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
    
    @JsonProperty("account") 
    public AccountDTO getAccountInfo() {
        if (accountId != null) {
            AccountDTO accountDTO = new AccountDTO();
            
            accountDTO.setId(accountId.getId());
            accountDTO.setUsername(accountId.getUsername());
            accountDTO.setAvatar(accountId.getAvatar());
            accountDTO.setRole(accountId.getRole());
            
            return accountDTO;
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
}
