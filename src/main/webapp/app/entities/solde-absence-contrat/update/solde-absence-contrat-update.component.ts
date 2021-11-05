import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldeAbsenceContrat, SoldeAbsenceContrat } from '../solde-absence-contrat.model';
import { SoldeAbsenceContratService } from '../service/solde-absence-contrat.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

@Component({
  selector: 'jhi-solde-absence-contrat-update',
  templateUrl: './solde-absence-contrat-update.component.html',
})
export class SoldeAbsenceContratUpdateComponent implements OnInit {
  isSaving = false;

  typeContratsSharedCollection: ITypeContrat[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [],
    fullPayRight: [],
    halfPayRight: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    typeContrat: [],
  });

  constructor(
    protected soldeAbsenceContratService: SoldeAbsenceContratService,
    protected typeContratService: TypeContratService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsenceContrat }) => {
      if (soldeAbsenceContrat.id === undefined) {
        const today = dayjs().startOf('day');
        soldeAbsenceContrat.dateop = today;
        soldeAbsenceContrat.createdDate = today;
        soldeAbsenceContrat.modifiedDate = today;
      }

      this.updateForm(soldeAbsenceContrat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldeAbsenceContrat = this.createFromForm();
    if (soldeAbsenceContrat.id !== undefined) {
      this.subscribeToSaveResponse(this.soldeAbsenceContratService.update(soldeAbsenceContrat));
    } else {
      this.subscribeToSaveResponse(this.soldeAbsenceContratService.create(soldeAbsenceContrat));
    }
  }

  trackTypeContratById(index: number, item: ITypeContrat): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldeAbsenceContrat>>): void {
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

  protected updateForm(soldeAbsenceContrat: ISoldeAbsenceContrat): void {
    this.editForm.patchValue({
      id: soldeAbsenceContrat.id,
      annee: soldeAbsenceContrat.annee,
      fullPayRight: soldeAbsenceContrat.fullPayRight,
      halfPayRight: soldeAbsenceContrat.halfPayRight,
      util: soldeAbsenceContrat.util,
      dateop: soldeAbsenceContrat.dateop ? soldeAbsenceContrat.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: soldeAbsenceContrat.modifiedBy,
      op: soldeAbsenceContrat.op,
      isDeleted: soldeAbsenceContrat.isDeleted,
      createdDate: soldeAbsenceContrat.createdDate ? soldeAbsenceContrat.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: soldeAbsenceContrat.modifiedDate ? soldeAbsenceContrat.modifiedDate.format(DATE_TIME_FORMAT) : null,
      typeContrat: soldeAbsenceContrat.typeContrat,
    });

    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing(
      this.typeContratsSharedCollection,
      soldeAbsenceContrat.typeContrat
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing(typeContrats, this.editForm.get('typeContrat')!.value)
        )
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));
  }

  protected createFromForm(): ISoldeAbsenceContrat {
    return {
      ...new SoldeAbsenceContrat(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      fullPayRight: this.editForm.get(['fullPayRight'])!.value,
      halfPayRight: this.editForm.get(['halfPayRight'])!.value,
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
      typeContrat: this.editForm.get(['typeContrat'])!.value,
    };
  }
}
