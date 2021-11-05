package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperatorForm.
 */
@Entity
@Table(name = "pr_operator_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperatorForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "op")
    private String op;

    @Column(name = "util")
    private String util;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OperatorForm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public OperatorForm code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public OperatorForm libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public OperatorForm libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public OperatorForm dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public OperatorForm modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OperatorForm createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public OperatorForm op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public OperatorForm util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public OperatorForm isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public OperatorForm createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public OperatorForm modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatorForm)) {
            return false;
        }
        return id != null && id.equals(((OperatorForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperatorForm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", util='" + getUtil() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
