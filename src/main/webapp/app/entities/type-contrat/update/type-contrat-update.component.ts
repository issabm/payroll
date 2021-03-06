import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITypeContrat, TypeContrat } from '../type-contrat.model';
import { TypeContratService } from '../service/type-contrat.service';

@Component({
  selector: 'jhi-type-contrat-update',
  templateUrl: './type-contrat-update.component.html',
})
export class TypeContratUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected typeContratService: TypeContratService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeContrat }) => {
      if (typeContrat.id === undefined) {
        const today = dayjs().startOf('day');
        typeContrat.dateop = today;
        typeContrat.createdDate = today;
        typeContrat.modifiedDate = today;
      }

      this.updateForm(typeContrat);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeContrat = this.createFromForm();
    if (typeContrat.id !== undefined) {
      this.subscribeToSaveResponse(this.typeContratService.update(typeContrat));
    } else {
      this.subscribeToSaveResponse(this.typeContratService.create(typeContrat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeContrat>>): void {
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

  protected updateForm(typeContrat: ITypeContrat): void {
    this.editForm.patchValue({
      id: typeContrat.id,
      code: typeContrat.code,
      libAr: typeContrat.libAr,
      libEn: typeContrat.libEn,
      util: typeContrat.util,
      dateop: typeContrat.dateop ? typeContrat.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: typeContrat.modifiedBy,
      op: typeContrat.op,
      isDeleted: typeContrat.isDeleted,
      createdDate: typeContrat.createdDate ? typeContrat.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: typeContrat.modifiedDate ? typeContrat.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITypeContrat {
    return {
      ...new TypeContrat(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
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
    };
  }
}
