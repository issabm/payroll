import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureAbsenceRebrique, NatureAbsenceRebrique } from '../nature-absence-rebrique.model';
import { NatureAbsenceRebriqueService } from '../service/nature-absence-rebrique.service';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';

@Component({
  selector: 'jhi-nature-absence-rebrique-update',
  templateUrl: './nature-absence-rebrique-update.component.html',
})
export class NatureAbsenceRebriqueUpdateComponent implements OnInit {
  isSaving = false;

  natureAbsencesSharedCollection: INatureAbsence[] = [];
  rebriquesSharedCollection: IRebrique[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    valueTaken: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    natureAbsence: [],
    rebrique: [],
  });

  constructor(
    protected natureAbsenceRebriqueService: NatureAbsenceRebriqueService,
    protected natureAbsenceService: NatureAbsenceService,
    protected rebriqueService: RebriqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAbsenceRebrique }) => {
      if (natureAbsenceRebrique.id === undefined) {
        const today = dayjs().startOf('day');
        natureAbsenceRebrique.dateop = today;
        natureAbsenceRebrique.createdDate = today;
        natureAbsenceRebrique.modifiedDate = today;
      }

      this.updateForm(natureAbsenceRebrique);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureAbsenceRebrique = this.createFromForm();
    if (natureAbsenceRebrique.id !== undefined) {
      this.subscribeToSaveResponse(this.natureAbsenceRebriqueService.update(natureAbsenceRebrique));
    } else {
      this.subscribeToSaveResponse(this.natureAbsenceRebriqueService.create(natureAbsenceRebrique));
    }
  }

  trackNatureAbsenceById(index: number, item: INatureAbsence): number {
    return item.id!;
  }

  trackRebriqueById(index: number, item: IRebrique): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureAbsenceRebrique>>): void {
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

  protected updateForm(natureAbsenceRebrique: INatureAbsenceRebrique): void {
    this.editForm.patchValue({
      id: natureAbsenceRebrique.id,
      code: natureAbsenceRebrique.code,
      libAr: natureAbsenceRebrique.libAr,
      libEn: natureAbsenceRebrique.libEn,
      valueTaken: natureAbsenceRebrique.valueTaken,
      util: natureAbsenceRebrique.util,
      dateop: natureAbsenceRebrique.dateop ? natureAbsenceRebrique.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: natureAbsenceRebrique.modifiedBy,
      op: natureAbsenceRebrique.op,
      isDeleted: natureAbsenceRebrique.isDeleted,
      createdDate: natureAbsenceRebrique.createdDate ? natureAbsenceRebrique.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureAbsenceRebrique.modifiedDate ? natureAbsenceRebrique.modifiedDate.format(DATE_TIME_FORMAT) : null,
      natureAbsence: natureAbsenceRebrique.natureAbsence,
      rebrique: natureAbsenceRebrique.rebrique,
    });

    this.natureAbsencesSharedCollection = this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(
      this.natureAbsencesSharedCollection,
      natureAbsenceRebrique.natureAbsence
    );
    this.rebriquesSharedCollection = this.rebriqueService.addRebriqueToCollectionIfMissing(
      this.rebriquesSharedCollection,
      natureAbsenceRebrique.rebrique
    );
  }

  protected loadRelationshipsOptions(): void {
    this.natureAbsenceService
      .query()
      .pipe(map((res: HttpResponse<INatureAbsence[]>) => res.body ?? []))
      .pipe(
        map((natureAbsences: INatureAbsence[]) =>
          this.natureAbsenceService.addNatureAbsenceToCollectionIfMissing(natureAbsences, this.editForm.get('natureAbsence')!.value)
        )
      )
      .subscribe((natureAbsences: INatureAbsence[]) => (this.natureAbsencesSharedCollection = natureAbsences));

    this.rebriqueService
      .query()
      .pipe(map((res: HttpResponse<IRebrique[]>) => res.body ?? []))
      .pipe(
        map((rebriques: IRebrique[]) =>
          this.rebriqueService.addRebriqueToCollectionIfMissing(rebriques, this.editForm.get('rebrique')!.value)
        )
      )
      .subscribe((rebriques: IRebrique[]) => (this.rebriquesSharedCollection = rebriques));
  }

  protected createFromForm(): INatureAbsenceRebrique {
    return {
      ...new NatureAbsenceRebrique(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      valueTaken: this.editForm.get(['valueTaken'])!.value,
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
      natureAbsence: this.editForm.get(['natureAbsence'])!.value,
      rebrique: this.editForm.get(['rebrique'])!.value,
    };
  }
}
