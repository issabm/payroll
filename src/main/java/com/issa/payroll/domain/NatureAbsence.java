package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NatureAbsence.
 */
@Entity
@Table(name = "ref_nature_absence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NatureAbsence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "full_pay")
    private Integer fullPay;

    @Column(name = "half_pay")
    private Integer halfPay;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "op")
    private String op;

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

    public NatureAbsence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public NatureAbsence code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public NatureAbsence libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public NatureAbsence libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getUtil() {
        return this.util;
    }

    public NatureAbsence util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public NatureAbsence dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public Integer getFullPay() {
        return this.fullPay;
    }

    public NatureAbsence fullPay(Integer fullPay) {
        this.setFullPay(fullPay);
        return this;
    }

    public void setFullPay(Integer fullPay) {
        this.fullPay = fullPay;
    }

    public Integer getHalfPay() {
        return this.halfPay;
    }

    public NatureAbsence halfPay(Integer halfPay) {
        this.setHalfPay(halfPay);
        return this;
    }

    public void setHalfPay(Integer halfPay) {
        this.halfPay = halfPay;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public NatureAbsence modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public NatureAbsence op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public NatureAbsence isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public NatureAbsence createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public NatureAbsence modifiedDate(ZonedDateTime modifiedDate) {
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
        if (!(o instanceof NatureAbsence)) {
            return false;
        }
        return id != null && id.equals(((NatureAbsence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureAbsence{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", fullPay=" + getFullPay() +
            ", halfPay=" + getHalfPay() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
