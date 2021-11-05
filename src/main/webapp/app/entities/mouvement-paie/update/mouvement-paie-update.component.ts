import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';
import { MouvementPaieService } from '../service/mouvement-paie.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';

@Component({
  selector: 'jhi-mouvement-paie-update',
  templateUrl: './mouvement-paie-update.component.html',
})
export class MouvementPaieUpdateComponent implements OnInit {
  isSaving = false;

  situationsSharedCollection: ISituation[] = [];
  groupesSharedCollection: IGroupe[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    lib: [],
    annee: [],
    mois: [],
    dateCalcul: [],
    dateValid: [],
    datePayroll: [],
    totalCotis: [],
    totalRetenue: [],
    totalTaxable: [],
    totalTax: [],
    totalNet: [],
    totalNetDevise: [],
    tauxChange: [],
    calculBy: [],
    effectBy: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    situation: [],
    groupe: [],
    entreprise: [],
    affiliation: [],
  });

  constructor(
    protected mouvementPaieService: MouvementPaieService,
    protected situationService: SituationService,
    protected groupeService: GroupeService,
    protected entrepriseService: EntrepriseService,
    protected affiliationService: AffiliationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementPaie }) => {
      if (mouvementPaie.id === undefined) {
        const today = dayjs().startOf('day');
        mouvementPaie.dateop = today;
        mouvementPaie.createdDate = today;
        mouvementPaie.modifiedDate = today;
      }

      this.updateForm(mouvementPaie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvementPaie = this.createFromForm();
    if (mouvementPaie.id !== undefined) {
      this.subscribeToSaveResponse(this.mouvementPaieService.update(mouvementPaie));
    } else {
      this.subscribeToSaveResponse(this.mouvementPaieService.create(mouvementPaie));
    }
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvementPaie>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mouvementPaie: IMouvementPaie): void {
    this.editForm.patchValue({
      id: mouvementPaie.id,
      code: mouvementPaie.code,
      lib: mouvementPaie.lib,
      annee: mouvementPaie.annee,
      mois: mouvementPaie.mois,
      dateCalcul: mouvementPaie.dateCalcul,
      dateValid: mouvementPaie.dateValid,
      datePayroll: mouvementPaie.datePayroll,
      totalCotis: mouvementPaie.totalCotis,
      totalRetenue: mouvementPaie.totalRetenue,
      totalTaxable: mouvementPaie.totalTaxable,
      totalTax: mouvementPaie.totalTax,
      totalNet: mouvementPaie.totalNet,
      totalNetDevise: mouvementPaie.totalNetDevise,
      tauxChange: mouvementPaie.tauxChange,
      calculBy: mouvementPaie.calculBy,
      effectBy: mouvementPaie.effectBy,
      dateSituation: mouvementPaie.dateSituation,
      dateop: mouvementPaie.dateop ? mouvementPaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: mouvementPaie.modifiedBy,
      createdBy: mouvementPaie.createdBy,
      op: mouvementPaie.op,
      util: mouvementPaie.util,
      isDeleted: mouvementPaie.isDeleted,
      createdDate: mouvementPaie.createdDate ? mouvementPaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: mouvementPaie.modifiedDate ? mouvementPaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
      situation: mouvementPaie.situation,
      groupe: mouvementPaie.groupe,
      entreprise: mouvementPaie.entreprise,
      affiliation: mouvementPaie.affiliation,
    });

    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      mouvementPaie.situation
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, mouvementPaie.groupe);
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      mouvementPaie.entreprise
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      mouvementPaie.affiliation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.situationService
      .query()
      .pipe(map((res: HttpResponse<ISituation[]>) => res.body ?? []))
      .pipe(
        map((situations: ISituation[]) =>
          this.situationService.addSituationToCollectionIfMissing(situations, this.editForm.get('situation')!.value)
        )
      )
      .subscribe((situations: ISituation[]) => (this.situationsSharedCollection = situations));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('affiliation')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));
  }

  protected createFromForm(): IMouvementPaie {
    return {
      ...new MouvementPaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      lib: this.editForm.get(['lib'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      dateCalcul: this.editForm.get(['dateCalcul'])!.value,
      dateValid: this.editForm.get(['dateValid'])!.value,
      datePayroll: this.editForm.get(['datePayroll'])!.value,
      totalCotis: this.editForm.get(['totalCotis'])!.value,
      totalRetenue: this.editForm.get(['totalRetenue'])!.value,
      totalTaxable: this.editForm.get(['totalTaxable'])!.value,
      totalTax: this.editForm.get(['totalTax'])!.value,
      totalNet: this.editForm.get(['totalNet'])!.value,
      totalNetDevise: this.editForm.get(['totalNetDevise'])!.value,
      tauxChange: this.editForm.get(['tauxChange'])!.value,
      calculBy: this.editForm.get(['calculBy'])!.value,
      effectBy: this.editForm.get(['effectBy'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      situation: this.editForm.get(['situation'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      affiliation: this.editForm.get(['affiliation'])!.value,
    };
  }
}
