import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INatureAbsence, NatureAbsence } from '../nature-absence.model';
import { NatureAbsenceService } from '../service/nature-absence.service';

@Component({
  selector: 'jhi-nature-absence-update',
  templateUrl: './nature-absence-update.component.html',
})
export class NatureAbsenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    util: [],
    dateop: [],
    fullPay: [],
    halfPay: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected natureAbsenceService: NatureAbsenceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAbsence }) => {
      if (natureAbsence.id === undefined) {
        const today = dayjs().startOf('day');
        natureAbsence.dateop = today;
        natureAbsence.createdDate = today;
        natureAbsence.modifiedDate = today;
      }

      this.updateForm(natureAbsence);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureAbsence = this.createFromForm();
    if (natureAbsence.id !== undefined) {
      this.subscribeToSaveResponse(this.natureAbsenceService.update(natureAbsence));
    } else {
      this.subscribeToSaveResponse(this.natureAbsenceService.create(natureAbsence));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureAbsence>>): void {
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

  protected updateForm(natureAbsence: INatureAbsence): void {
    this.editForm.patchValue({
      id: natureAbsence.id,
      code: natureAbsence.code,
      libAr: natureAbsence.libAr,
      libEn: natureAbsence.libEn,
      util: natureAbsence.util,
      dateop: natureAbsence.dateop ? natureAbsence.dateop.format(DATE_TIME_FORMAT) : null,
      fullPay: natureAbsence.fullPay,
      halfPay: natureAbsence.halfPay,
      modifiedBy: natureAbsence.modifiedBy,
      op: natureAbsence.op,
      isDeleted: natureAbsence.isDeleted,
      createdDate: natureAbsence.createdDate ? natureAbsence.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: natureAbsence.modifiedDate ? natureAbsence.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INatureAbsence {
    return {
      ...new NatureAbsence(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      fullPay: this.editForm.get(['fullPay'])!.value,
      halfPay: this.editForm.get(['halfPay'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
