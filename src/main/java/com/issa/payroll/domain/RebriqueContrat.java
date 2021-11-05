package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RebriqueContrat.
 */
@Entity
@Table(name = "ref_reb_contrat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RebriqueContrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private Rebrique rebrqiue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "typeContrat" }, allowSetters = true)
    private SousTypeContrat sousTypeContrat;

    @ManyToOne
    private TypeContrat typeContrat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RebriqueContrat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtil() {
        return this.util;
    }

    public RebriqueContrat util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public RebriqueContrat dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public RebriqueContrat dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public RebriqueContrat modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RebriqueContrat createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public RebriqueContrat op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public RebriqueContrat isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public RebriqueContrat createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public RebriqueContrat modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Rebrique getRebrqiue() {
        return this.rebrqiue;
    }

    public void setRebrqiue(Rebrique rebrique) {
        this.rebrqiue = rebrique;
    }

    public RebriqueContrat rebrqiue(Rebrique rebrique) {
        this.setRebrqiue(rebrique);
        return this;
    }

    public SousTypeContrat getSousTypeContrat() {
        return this.sousTypeContrat;
    }

    public void setSousTypeContrat(SousTypeContrat sousTypeContrat) {
        this.sousTypeContrat = sousTypeContrat;
    }

    public RebriqueContrat sousTypeContrat(SousTypeContrat sousTypeContrat) {
        this.setSousTypeContrat(sousTypeContrat);
        return this;
    }

    public TypeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public RebriqueContrat typeContrat(TypeContrat typeContrat) {
        this.setTypeContrat(typeContrat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RebriqueContrat)) {
            return false;
        }
        return id != null && id.equals(((RebriqueContrat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RebriqueContrat{" +
            "id=" + getId() +
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
