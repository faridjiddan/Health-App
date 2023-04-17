package com.xa.filteringtest2_api.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "m_customer_relation")
@Where(clause = "is_delete=false")
public class CustomerRelation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="name", length = 50)
    private String Name;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_on")
    private Timestamp deletedOn;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    public User userCreated;

    @ManyToOne
    @JoinColumn(name = "modified_by", insertable = false, updatable = false)
    public User userModified;

    @ManyToOne
    @JoinColumn(name = "deleted_by", insertable = false, updatable = false)
    public User userDeleted;

    @Column(name = "is_delete")
    private Boolean isDelete = Boolean.FALSE;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public User getUserModified() {
        return userModified;
    }

    public void setUserModified(User userModified) {
        this.userModified = userModified;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(User userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
        
}
