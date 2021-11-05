import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMatricePaie, MatricePaie } from '../matrice-paie.model';
import { MatricePaieService } from '../service/matrice-paie.service';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { EchlonService } from 'app/entities/echlon/service/echlon.service';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { EmploiService } from 'app/entities/emploi/service/emploi.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';

@Component({
  selector: 'jhi-matrice-paie-update',
  templateUrl: './matrice-paie-update.component.html',
})
export class MatricePaieUpdateComponent implements OnInit {
  isSaving = false;

  assietesSharedCollection: IAssiete[] = [];
  echlonsSharedCollection: IEchlon[] = [];
  emploisSharedCollection: IEmploi[] = [];
  categoriesSharedCollection: ICategory[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  sexesSharedCollection: ISexe[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    isDisplay: [],
    anneeDebut: [],
    moisDebut: [],
    anneeFin: [],
    moisFin: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    assiete: [],
    echlon: [],
    emploi: [],
    category: [],
    affilication: [],
    entreprise: [],
    groupe: [],
    sexe: [],
  });

  constructor(
    protected matricePaieService: MatricePaieService,
    protected assieteService: AssieteService,
    protected echlonService: EchlonService,
    protected emploiService: EmploiService,
    protected categoryService: CategoryService,
    protected affiliationService: AffiliationService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected sexeService: SexeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricePaie }) => {
      if (matricePaie.id === undefined) {
        const today = dayjs().startOf('day');
        matricePaie.dateop = today;
        matricePaie.createdDate = today;
        matricePaie.modifiedDate = today;
      }

      this.updateForm(matricePaie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matricePaie = this.createFromForm();
    if (matricePaie.id !== undefined) {
      this.subscribeToSaveResponse(this.matricePaieService.update(matricePaie));
    } else {
      this.subscribeToSaveResponse(this.matricePaieService.create(matricePaie));
    }
  }

  trackAssieteById(index: number, item: IAssiete): number {
    return item.id!;
  }

  trackEchlonById(index: number, item: IEchlon): number {
    return item.id!;
  }

  trackEmploiById(index: number, item: IEmploi): number {
    return item.id!;
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatricePaie>>): void {
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

  protected updateForm(matricePaie: IMatricePaie): void {
    this.editForm.patchValue({
      id: matricePaie.id,
      code: matricePaie.code,
      libAr: matricePaie.libAr,
      libEn: matricePaie.libEn,
      isDisplay: matricePaie.isDisplay,
      anneeDebut: matricePaie.anneeDebut,
      moisDebut: matricePaie.moisDebut,
      anneeFin: matricePaie.anneeFin,
      moisFin: matricePaie.moisFin,
      util: matricePaie.util,
      dateop: matricePaie.dateop ? matricePaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: matricePaie.modifiedBy,
      op: matricePaie.op,
      isDeleted: matricePaie.isDeleted,
      createdDate: matricePaie.createdDate ? matricePaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: matricePaie.modifiedDate ? matricePaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
      assiete: matricePaie.assiete,
      echlon: matricePaie.echlon,
      emploi: matricePaie.emploi,
      category: matricePaie.category,
      affilication: matricePaie.affilication,
      entreprise: matricePaie.entreprise,
      groupe: matricePaie.groupe,
      sexe: matricePaie.sexe,
    });

    this.assietesSharedCollection = this.assieteService.addAssieteToCollectionIfMissing(this.assietesSharedCollection, matricePaie.assiete);
    this.echlonsSharedCollection = this.echlonService.addEchlonToCollectionIfMissing(this.echlonsSharedCollection, matricePaie.echlon);
    this.emploisSharedCollection = this.emploiService.addEmploiToCollectionIfMissing(this.emploisSharedCollection, matricePaie.emploi);
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      matricePaie.category
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      matricePaie.affilication
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      matricePaie.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, matricePaie.groupe);
    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, matricePaie.sexe);
  }

  protected loadRelationshipsOptions(): void {
    this.assieteService
      .query()
      .pipe(map((res: HttpResponse<IAssiete[]>) => res.body ?? []))
      .pipe(
        map((assietes: IAssiete[]) => this.assieteService.addAssieteToCollectionIfMissing(assietes, this.editForm.get('assiete')!.value))
      )
      .subscribe((assietes: IAssiete[]) => (this.assietesSharedCollection = assietes));

    this.echlonService
      .query()
      .pipe(map((res: HttpResponse<IEchlon[]>) => res.body ?? []))
      .pipe(map((echlons: IEchlon[]) => this.echlonService.addEchlonToCollectionIfMissing(echlons, this.editForm.get('echlon')!.value)))
      .subscribe((echlons: IEchlon[]) => (this.echlonsSharedCollection = echlons));

    this.emploiService
      .query()
      .pipe(map((res: HttpResponse<IEmploi[]>) => res.body ?? []))
      .pipe(map((emplois: IEmploi[]) => this.emploiService.addEmploiToCollectionIfMissing(emplois, this.editForm.get('emploi')!.value)))
      .subscribe((emplois: IEmploi[]) => (this.emploisSharedCollection = emplois));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('affilication')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexe[]>) => res.body ?? []))
      .pipe(map((sexes: ISexe[]) => this.sexeService.addSexeToCollectionIfMissing(sexes, this.editForm.get('sexe')!.value)))
      .subscribe((sexes: ISexe[]) => (this.sexesSharedCollection = sexes));
  }

  protected createFromForm(): IMatricePaie {
    return {
      ...new MatricePaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      isDisplay: this.editForm.get(['isDisplay'])!.value,
      anneeDebut: this.editForm.get(['anneeDebut'])!.value,
      moisDebut: this.editForm.get(['moisDebut'])!.value,
      anneeFin: this.editForm.get(['anneeFin'])!.value,
      moisFin: this.editForm.get(['moisFin'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      assiete: this.editForm.get(['assiete'])!.value,
      echlon: this.editForm.get(['echlon'])!.value,
      emploi: this.editForm.get(['emploi'])!.value,
      category: this.editForm.get(['category'])!.value,
      affilication: this.editForm.get(['affilication'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
    };
  }
}
