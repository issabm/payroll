package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormPaieLigne.
 */
@Entity
@Table(name = "pr_form_ligne")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormPaieLigne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

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

    @ManyToOne
    private FormPaie formPaie;

    @ManyToOne
    private OperatorForm operatorForm;

    @ManyToOne
    private Assiete assiete;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormPaieLigne id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public FormPaieLigne priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public String getCode() {
        return this.code;
    }

    public FormPaieLigne code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public FormPaieLigne libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public FormPaieLigne libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public FormPaieLigne dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public FormPaieLigne modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FormPaieLigne createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public FormPaieLigne op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public FormPaieLigne util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public FormPaieLigne isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public FormPaieLigne createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public FormPaieLigne modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public FormPaie getFormPaie() {
        return this.formPaie;
    }

    public void setFormPaie(FormPaie formPaie) {
        this.formPaie = formPaie;
    }

    public FormPaieLigne formPaie(FormPaie formPaie) {
        this.setFormPaie(formPaie);
        return this;
    }

    public OperatorForm getOperatorForm() {
        return this.operatorForm;
    }

    public void setOperatorForm(OperatorForm operatorForm) {
        this.operatorForm = operatorForm;
    }

    public FormPaieLigne operatorForm(OperatorForm operatorForm) {
        this.setOperatorForm(operatorForm);
        return this;
    }

    public Assiete getAssiete() {
        return this.assiete;
    }

    public void setAssiete(Assiete assiete) {
        this.assiete = assiete;
    }

    public FormPaieLigne assiete(Assiete assiete) {
        this.setAssiete(assiete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormPaieLigne)) {
            return false;
        }
        return id != null && id.equals(((FormPaieLigne) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormPaieLigne{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
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
