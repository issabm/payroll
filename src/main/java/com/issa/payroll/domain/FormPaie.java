package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormPaie.
 */
@Entity
@Table(name = "pr_form_payroll")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormPaie implements Serializable {

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

    @Column(name = "anne_debut")
    private Integer anneDebut;

    @Column(name = "annee_fin")
    private Integer anneeFin;

    @Column(name = "mois_debut")
    private Integer moisDebut;

    @Column(name = "mois_fin")
    private Integer moisFin;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "util")
    private String util;

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

    public FormPaie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public FormPaie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public FormPaie libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public FormPaie libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public Integer getAnneDebut() {
        return this.anneDebut;
    }

    public FormPaie anneDebut(Integer anneDebut) {
        this.setAnneDebut(anneDebut);
        return this;
    }

    public void setAnneDebut(Integer anneDebut) {
        this.anneDebut = anneDebut;
    }

    public Integer getAnneeFin() {
        return this.anneeFin;
    }

    public FormPaie anneeFin(Integer anneeFin) {
        this.setAnneeFin(anneeFin);
        return this;
    }

    public void setAnneeFin(Integer anneeFin) {
        this.anneeFin = anneeFin;
    }

    public Integer getMoisDebut() {
        return this.moisDebut;
    }

    public FormPaie moisDebut(Integer moisDebut) {
        this.setMoisDebut(moisDebut);
        return this;
    }

    public void setMoisDebut(Integer moisDebut) {
        this.moisDebut = moisDebut;
    }

    public Integer getMoisFin() {
        return this.moisFin;
    }

    public FormPaie moisFin(Integer moisFin) {
        this.setMoisFin(moisFin);
        return this;
    }

    public void setMoisFin(Integer moisFin) {
        this.moisFin = moisFin;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public FormPaie dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public FormPaie modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FormPaie createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUtil() {
        return this.util;
    }

    public FormPaie util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getOp() {
        return this.op;
    }

    public FormPaie op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public FormPaie isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public FormPaie createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public FormPaie modifiedDate(ZonedDateTime modifiedDate) {
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
        if (!(o instanceof FormPaie)) {
            return false;
        }
        return id != null && id.equals(((FormPaie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormPaie{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", anneDebut=" + getAnneDebut() +
            ", anneeFin=" + getAnneeFin() +
            ", moisDebut=" + getMoisDebut() +
            ", moisFin=" + getMoisFin() +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", util='" + getUtil() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
