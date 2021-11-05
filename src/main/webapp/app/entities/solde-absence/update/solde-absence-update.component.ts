import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldeAbsence, SoldeAbsence } from '../solde-absence.model';
import { SoldeAbsenceService } from '../service/solde-absence.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';

@Component({
  selector: 'jhi-solde-absence-update',
  templateUrl: './solde-absence-update.component.html',
})
export class SoldeAbsenceUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];
  natureAbsencesSharedCollection: INatureAbsence[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [],
    fullPayRight: [],
    halfPayRight: [],
    fullPay: [],
    halfPay: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    affectation: [],
    natureAbsence: [],
  });

  constructor(
    protected soldeAbsenceService: SoldeAbsenceService,
    protected employeService: EmployeService,
    protected natureAbsenceService: NatureAbsenceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsence }) => {
      if (soldeAbsence.id === undefined) {
        const today = dayjs().startOf('day');
        soldeAbsence.dateop = today;
        soldeAbsence.createdDate = today;
        soldeAbsence.modifiedDate = today;
      }

      this.updateForm(soldeAbsence);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldeAbsence = this.createFromForm();
    if (soldeAbsence.id !== undefined) {
      this.subscribeToSaveResponse(this.soldeAbsenceService.update(soldeAbsence));
    } else {
      this.subscribeToSaveResponse(this.soldeAbsenceService.create(soldeAbsence));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackNatureAbsenceById(index: number, item: INatureAbsence): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldeAbsence>>): void {
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

  protected updateForm(soldeAbsence: ISoldeAbsence): void {
    this.editForm.patchValue({
      id: soldeAbsence.id,
      annee: soldeAbsence.annee,
      fullPayRight: soldeAbsence.fullPayRight,
      halfPayRight: soldeAbsence.halfPayRight,
      fullPay: soldeAbsence.fullPay,
      halfPay: soldeAbsence.halfPay,
      util: soldeAbsence.util,
      dateop: soldeAbsence.dateop ? soldeAbsence.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: soldeAbsence.modifiedBy,
      op: soldeAbsence.op,
      isDeleted: soldeAbsence.isDeleted,
      createdDate: soldeAbsence.createdDate ? soldeAbsence.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: soldeAbsence.modifiedDate ? soldeAbsence.modifiedDate.format(DATE_TIME_FORMAT) : null,
      affectation: soldeAbsence.affectation,
      natureAbsence: soldeAbsence.natureAbsence,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(
      this.employesSharedCollection,
      soldeAbsence.affectation
    );
    this.natureAbsencesSharedCollection = this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(
      this.natureAbsencesSharedCollection,
      soldeAbsence.natureAbsence
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) =>
          this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('affectation')!.value)
        )
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));

    this.natureAbsenceService
      .query()
      .pipe(map((res: HttpResponse<INatureAbsence[]>) => res.body ?? []))
      .pipe(
        map((natureAbsences: INatureAbsence[]) =>
          this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(natureAbsences, this.editForm.get('natureAbsence')!.value)
        )
      )
      .subscribe((natureAbsences: INatureAbsence[]) => (this.natureAbsencesSharedCollection = natureAbsences));
  }

  protected createFromForm(): ISoldeAbsence {
    return {
      ...new SoldeAbsence(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      fullPayRight: this.editForm.get(['fullPayRight'])!.value,
      halfPayRight: this.editForm.get(['halfPayRight'])!.value,
      fullPay: this.editForm.get(['fullPay'])!.value,
      halfPay: this.editForm.get(['halfPay'])!.value,
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
      affectation: this.editForm.get(['affectation'])!.value,
      natureAbsence: this.editForm.get(['natureAbsence'])!.value,
    };
  }
}
