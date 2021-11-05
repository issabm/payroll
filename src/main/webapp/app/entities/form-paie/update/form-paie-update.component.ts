import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFormPaie, FormPaie } from '../form-paie.model';
import { FormPaieService } from '../service/form-paie.service';

@Component({
  selector: 'jhi-form-paie-update',
  templateUrl: './form-paie-update.component.html',
})
export class FormPaieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    anneDebut: [],
    anneeFin: [],
    moisDebut: [],
    moisFin: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    util: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected formPaieService: FormPaieService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaie }) => {
      if (formPaie.id === undefined) {
        const today = dayjs().startOf('day');
        formPaie.dateop = today;
        formPaie.createdDate = today;
        formPaie.modifiedDate = today;
      }

      this.updateForm(formPaie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formPaie = this.createFromForm();
    if (formPaie.id !== undefined) {
      this.subscribeToSaveResponse(this.formPaieService.update(formPaie));
    } else {
      this.subscribeToSaveResponse(this.formPaieService.create(formPaie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormPaie>>): void {
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

  protected updateForm(formPaie: IFormPaie): void {
    this.editForm.patchValue({
      id: formPaie.id,
      code: formPaie.code,
      libEn: formPaie.libEn,
      libAr: formPaie.libAr,
      anneDebut: formPaie.anneDebut,
      anneeFin: formPaie.anneeFin,
      moisDebut: formPaie.moisDebut,
      moisFin: formPaie.moisFin,
      dateop: formPaie.dateop ? formPaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: formPaie.modifiedBy,
      createdBy: formPaie.createdBy,
      util: formPaie.util,
      op: formPaie.op,
      isDeleted: formPaie.isDeleted,
      createdDate: formPaie.createdDate ? formPaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: formPaie.modifiedDate ? formPaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IFormPaie {
    return {
      ...new FormPaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      anneDebut: this.editForm.get(['anneDebut'])!.value,
      anneeFin: this.editForm.get(['anneeFin'])!.value,
      moisDebut: this.editForm.get(['moisDebut'])!.value,
      moisFin: this.editForm.get(['moisFin'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      util: this.editForm.get(['util'])!.value,
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
