import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOperatorForm, OperatorForm } from '../operator-form.model';
import { OperatorFormService } from '../service/operator-form.service';

@Component({
  selector: 'jhi-operator-form-update',
  templateUrl: './operator-form-update.component.html',
})
export class OperatorFormUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected operatorFormService: OperatorFormService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operatorForm }) => {
      if (operatorForm.id === undefined) {
        const today = dayjs().startOf('day');
        operatorForm.dateop = today;
        operatorForm.createdDate = today;
        operatorForm.modifiedDate = today;
      }

      this.updateForm(operatorForm);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operatorForm = this.createFromForm();
    if (operatorForm.id !== undefined) {
      this.subscribeToSaveResponse(this.operatorFormService.update(operatorForm));
    } else {
      this.subscribeToSaveResponse(this.operatorFormService.create(operatorForm));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperatorForm>>): void {
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

  protected updateForm(operatorForm: IOperatorForm): void {
    this.editForm.patchValue({
      id: operatorForm.id,
      code: operatorForm.code,
      libEn: operatorForm.libEn,
      libAr: operatorForm.libAr,
      dateop: operatorForm.dateop ? operatorForm.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: operatorForm.modifiedBy,
      createdBy: operatorForm.createdBy,
      op: operatorForm.op,
      util: operatorForm.util,
      isDeleted: operatorForm.isDeleted,
      createdDate: operatorForm.createdDate ? operatorForm.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: operatorForm.modifiedDate ? operatorForm.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IOperatorForm {
    return {
      ...new OperatorForm(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
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
    };
  }
}
