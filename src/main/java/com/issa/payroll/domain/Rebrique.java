package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rebrique.
 */
@Entity
@Table(name = "pr_rebrique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rebrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "in_tax")
    private Boolean inTax;

    @Column(name = "in_social")
    private Boolean inSocial;

    @Column(name = "in_bp")
    private Boolean inBp;

    @Column(name = "val")
    private Double val;

    @Column(name = "date_situation")
    private LocalDate dateSituation;

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

    @ManyToOne
    private Sens sens;

    @ManyToOne
    private Concerne concerne;

    @ManyToOne
    private Frequence frequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rebrique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Rebrique priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public String getCode() {
        return this.code;
    }

    public Rebrique code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public Rebrique libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public Rebrique libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public Boolean getInTax() {
        return this.inTax;
    }

    public Rebrique inTax(Boolean inTax) {
        this.setInTax(inTax);
        return this;
    }

    public void setInTax(Boolean inTax) {
        this.inTax = inTax;
    }

    public Boolean getInSocial() {
        return this.inSocial;
    }

    public Rebrique inSocial(Boolean inSocial) {
        this.setInSocial(inSocial);
        return this;
    }

    public void setInSocial(Boolean inSocial) {
        this.inSocial = inSocial;
    }

    public Boolean getInBp() {
        return this.inBp;
    }

    public Rebrique inBp(Boolean inBp) {
        this.setInBp(inBp);
        return this;
    }

    public void setInBp(Boolean inBp) {
        this.inBp = inBp;
    }

    public Double getVal() {
        return this.val;
    }

    public Rebrique val(Double val) {
        this.setVal(val);
        return this;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public Rebrique dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Rebrique dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Rebrique modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Rebrique createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUtil() {
        return this.util;
    }

    public Rebrique util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getOp() {
        return this.op;
    }

    public Rebrique op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Rebrique isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Rebrique createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Rebrique modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Sens getSens() {
        return this.sens;
    }

    public void setSens(Sens sens) {
        this.sens = sens;
    }

    public Rebrique sens(Sens sens) {
        this.setSens(sens);
        return this;
    }

    public Concerne getConcerne() {
        return this.concerne;
    }

    public void setConcerne(Concerne concerne) {
        this.concerne = concerne;
    }

    public Rebrique concerne(Concerne concerne) {
        this.setConcerne(concerne);
        return this;
    }

    public Frequence getFrequence() {
        return this.frequence;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public Rebrique frequence(Frequence frequence) {
        this.setFrequence(frequence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rebrique)) {
            return false;
        }
        return id != null && id.equals(((Rebrique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rebrique{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", inTax='" + getInTax() + "'" +
            ", inSocial='" + getInSocial() + "'" +
            ", inBp='" + getInBp() + "'" +
            ", val=" + getVal() +
            ", dateSituation='" + getDateSituation() + "'" +
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
