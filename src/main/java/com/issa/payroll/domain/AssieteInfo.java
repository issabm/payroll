package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssieteInfo.
 */
@Entity
@Table(name = "pr_assiet_detai")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssieteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "val")
    private Double val;

    @Column(name = "util")
    private String util;

    @Column(name = "date_situation")
    private LocalDate dateSituation;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "op")
    private String op;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sens", "concerne", "frequence" }, allowSetters = true)
    private Rebrique rebrique;

    @ManyToOne
    private Assiete assiete;

    @ManyToOne
    private ModeCalcul modeCal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssieteInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public AssieteInfo priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public Double getVal() {
        return this.val;
    }

    public AssieteInfo val(Double val) {
        this.setVal(val);
        return this;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public String getUtil() {
        return this.util;
    }

    public AssieteInfo util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public AssieteInfo dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public AssieteInfo dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public AssieteInfo modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AssieteInfo createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public AssieteInfo op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public AssieteInfo isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public AssieteInfo createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public AssieteInfo modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Rebrique getRebrique() {
        return this.rebrique;
    }

    public void setRebrique(Rebrique rebrique) {
        this.rebrique = rebrique;
    }

    public AssieteInfo rebrique(Rebrique rebrique) {
        this.setRebrique(rebrique);
        return this;
    }

    public Assiete getAssiete() {
        return this.assiete;
    }

    public void setAssiete(Assiete assiete) {
        this.assiete = assiete;
    }

    public AssieteInfo assiete(Assiete assiete) {
        this.setAssiete(assiete);
        return this;
    }

    public ModeCalcul getModeCal() {
        return this.modeCal;
    }

    public void setModeCal(ModeCalcul modeCalcul) {
        this.modeCal = modeCalcul;
    }

    public AssieteInfo modeCal(ModeCalcul modeCalcul) {
        this.setModeCal(modeCalcul);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssieteInfo)) {
            return false;
        }
        return id != null && id.equals(((AssieteInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssieteInfo{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
            ", val=" + getVal() +
            ", util='" + getUtil() + "'" +
            ", dateSituation='" + getDateSituation() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
