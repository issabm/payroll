package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PalierImpo.
 */
@Entity
@Table(name = "cfg_global")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PalierImpo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "payroll_min")
    private Double payrollMin;

    @Column(name = "payroll_max")
    private Double payrollMax;

    @Column(name = "impo_value")
    private Double impoValue;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "date_modif")
    private ZonedDateTime dateModif;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "op")
    private String op;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    private Pays pays;

    @ManyToOne
    private Situation situation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PalierImpo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public PalierImpo annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getPayrollMin() {
        return this.payrollMin;
    }

    public PalierImpo payrollMin(Double payrollMin) {
        this.setPayrollMin(payrollMin);
        return this;
    }

    public void setPayrollMin(Double payrollMin) {
        this.payrollMin = payrollMin;
    }

    public Double getPayrollMax() {
        return this.payrollMax;
    }

    public PalierImpo payrollMax(Double payrollMax) {
        this.setPayrollMax(payrollMax);
        return this;
    }

    public void setPayrollMax(Double payrollMax) {
        this.payrollMax = payrollMax;
    }

    public Double getImpoValue() {
        return this.impoValue;
    }

    public PalierImpo impoValue(Double impoValue) {
        this.setImpoValue(impoValue);
        return this;
    }

    public void setImpoValue(Double impoValue) {
        this.impoValue = impoValue;
    }

    public String getUtil() {
        return this.util;
    }

    public PalierImpo util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public PalierImpo dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public ZonedDateTime getDateModif() {
        return this.dateModif;
    }

    public PalierImpo dateModif(ZonedDateTime dateModif) {
        this.setDateModif(dateModif);
        return this;
    }

    public void setDateModif(ZonedDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public PalierImpo modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public PalierImpo op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public PalierImpo isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public PalierImpo pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public Situation getSituation() {
        return this.situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public PalierImpo situation(Situation situation) {
        this.setSituation(situation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PalierImpo)) {
            return false;
        }
        return id != null && id.equals(((PalierImpo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PalierImpo{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", payrollMin=" + getPayrollMin() +
            ", payrollMax=" + getPayrollMax() +
            ", impoValue=" + getImpoValue() +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
