import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFormPaieLigneReb, FormPaieLigneReb } from '../form-paie-ligne-reb.model';
import { FormPaieLigneRebService } from '../service/form-paie-ligne-reb.service';
import { IFormPaieLigne } from 'app/entities/form-paie-ligne/form-paie-ligne.model';
import { FormPaieLigneService } from 'app/entities/form-paie-ligne/service/form-paie-ligne.service';

@Component({
  selector: 'jhi-form-paie-ligne-reb-update',
  templateUrl: './form-paie-ligne-reb-update.component.html',
})
export class FormPaieLigneRebUpdateComponent implements OnInit {
  isSaving = false;

  formPaieLignesSharedCollection: IFormPaieLigne[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    code: [],
    libEn: [],
    libAr: [],
    valOrigin: [],
    valCalcul: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    formPaieLigne: [],
  });

  constructor(
    protected formPaieLigneRebService: FormPaieLigneRebService,
    protected formPaieLigneService: FormPaieLigneService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaieLigneReb }) => {
      if (formPaieLigneReb.id === undefined) {
        const today = dayjs().startOf('day');
        formPaieLigneReb.dateop = today;
        formPaieLigneReb.createdDate = today;
        formPaieLigneReb.modifiedDate = today;
      }

      this.updateForm(formPaieLigneReb);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formPaieLigneReb = this.createFromForm();
    if (formPaieLigneReb.id !== undefined) {
      this.subscribeToSaveResponse(this.formPaieLigneRebService.update(formPaieLigneReb));
    } else {
      this.subscribeToSaveResponse(this.formPaieLigneRebService.create(formPaieLigneReb));
    }
  }

  trackFormPaieLigneById(index: number, item: IFormPaieLigne): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormPaieLigneReb>>): void {
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

  protected updateForm(formPaieLigneReb: IFormPaieLigneReb): void {
    this.editForm.patchValue({
      id: formPaieLigneReb.id,
      priorite: formPaieLigneReb.priorite,
      code: formPaieLigneReb.code,
      libEn: formPaieLigneReb.libEn,
      libAr: formPaieLigneReb.libAr,
      valOrigin: formPaieLigneReb.valOrigin,
      valCalcul: formPaieLigneReb.valCalcul,
      dateop: formPaieLigneReb.dateop ? formPaieLigneReb.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: formPaieLigneReb.modifiedBy,
      createdBy: formPaieLigneReb.createdBy,
      op: formPaieLigneReb.op,
      util: formPaieLigneReb.util,
      isDeleted: formPaieLigneReb.isDeleted,
      createdDate: formPaieLigneReb.createdDate ? formPaieLigneReb.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: formPaieLigneReb.modifiedDate ? formPaieLigneReb.modifiedDate.format(DATE_TIME_FORMAT) : null,
      formPaieLigne: formPaieLigneReb.formPaieLigne,
    });

    this.formPaieLignesSharedCollection = this.formPaieLigneService.addFormPaieLigneToCollectionIfMissing(
      this.formPaieLignesSharedCollection,
      formPaieLigneReb.formPaieLigne
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formPaieLigneService
      .query()
      .pipe(map((res: HttpResponse<IFormPaieLigne[]>) => res.body ?? []))
      .pipe(
        map((formPaieLignes: IFormPaieLigne[]) =>
          this.formPaieLigneService.addFormPaieLigneToCollectionIfMissing(formPaieLignes, this.editForm.get('formPaieLigne')!.value)
        )
      )
      .subscribe((formPaieLignes: IFormPaieLigne[]) => (this.formPaieLignesSharedCollection = formPaieLignes));
  }

  protected createFromForm(): IFormPaieLigneReb {
    return {
      ...new FormPaieLigneReb(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      valOrigin: this.editForm.get(['valOrigin'])!.value,
      valCalcul: this.editForm.get(['valCalcul'])!.value,
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
      formPaieLigne: this.editForm.get(['formPaieLigne'])!.value,
    };
  }
}
