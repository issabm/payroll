package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MatricePaie.
 */
@Entity
@Table(name = "pr_matrice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MatricePaie implements Serializable {

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

    @Column(name = "is_display")
    private Boolean isDisplay;

    @Column(name = "annee_debut")
    private Integer anneeDebut;

    @Column(name = "mois_debut")
    private Integer moisDebut;

    @Column(name = "annee_fin")
    private Integer anneeFin;

    @Column(name = "mois_fin")
    private Integer moisFin;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

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

    @ManyToOne
    private Assiete assiete;

    @ManyToOne
    private Echlon echlon;

    @ManyToOne
    private Emploi emploi;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entreprise", "direction", "pays", "devise" }, allowSetters = true)
    private Affiliation affilication;

    @ManyToOne
    @JsonIgnoreProperties(value = { "groupe", "pays", "mere", "devise" }, allowSetters = true)
    private Entreprise entreprise;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "devise" }, allowSetters = true)
    private Groupe groupe;

    @ManyToOne
    private Sexe sexe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatricePaie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public MatricePaie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public MatricePaie libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public MatricePaie libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public Boolean getIsDisplay() {
        return this.isDisplay;
    }

    public MatricePaie isDisplay(Boolean isDisplay) {
        this.setIsDisplay(isDisplay);
        return this;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getAnneeDebut() {
        return this.anneeDebut;
    }

    public MatricePaie anneeDebut(Integer anneeDebut) {
        this.setAnneeDebut(anneeDebut);
        return this;
    }

    public void setAnneeDebut(Integer anneeDebut) {
        this.anneeDebut = anneeDebut;
    }

    public Integer getMoisDebut() {
        return this.moisDebut;
    }

    public MatricePaie moisDebut(Integer moisDebut) {
        this.setMoisDebut(moisDebut);
        return this;
    }

    public void setMoisDebut(Integer moisDebut) {
        this.moisDebut = moisDebut;
    }

    public Integer getAnneeFin() {
        return this.anneeFin;
    }

    public MatricePaie anneeFin(Integer anneeFin) {
        this.setAnneeFin(anneeFin);
        return this;
    }

    public void setAnneeFin(Integer anneeFin) {
        this.anneeFin = anneeFin;
    }

    public Integer getMoisFin() {
        return this.moisFin;
    }

    public MatricePaie moisFin(Integer moisFin) {
        this.setMoisFin(moisFin);
        return this;
    }

    public void setMoisFin(Integer moisFin) {
        this.moisFin = moisFin;
    }

    public String getUtil() {
        return this.util;
    }

    public MatricePaie util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public MatricePaie dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public MatricePaie modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public MatricePaie op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MatricePaie isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public MatricePaie createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public MatricePaie modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Assiete getAssiete() {
        return this.assiete;
    }

    public void setAssiete(Assiete assiete) {
        this.assiete = assiete;
    }

    public MatricePaie assiete(Assiete assiete) {
        this.setAssiete(assiete);
        return this;
    }

    public Echlon getEchlon() {
        return this.echlon;
    }

    public void setEchlon(Echlon echlon) {
        this.echlon = echlon;
    }

    public MatricePaie echlon(Echlon echlon) {
        this.setEchlon(echlon);
        return this;
    }

    public Emploi getEmploi() {
        return this.emploi;
    }

    public void setEmploi(Emploi emploi) {
        this.emploi = emploi;
    }

    public MatricePaie emploi(Emploi emploi) {
        this.setEmploi(emploi);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MatricePaie category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Affiliation getAffilication() {
        return this.affilication;
    }

    public void setAffilication(Affiliation affiliation) {
        this.affilication = affiliation;
    }

    public MatricePaie affilication(Affiliation affiliation) {
        this.setAffilication(affiliation);
        return this;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public MatricePaie entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public MatricePaie groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public MatricePaie sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatricePaie)) {
            return false;
        }
        return id != null && id.equals(((MatricePaie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatricePaie{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", isDisplay='" + getIsDisplay() + "'" +
            ", anneeDebut=" + getAnneeDebut() +
            ", moisDebut=" + getMoisDebut() +
            ", anneeFin=" + getAnneeFin() +
            ", moisFin=" + getMoisFin() +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
